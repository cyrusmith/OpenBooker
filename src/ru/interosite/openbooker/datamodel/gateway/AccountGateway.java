package ru.interosite.openbooker.datamodel.gateway;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import ru.interosite.openbooker.datamodel.DBAccess;
import ru.interosite.openbooker.datamodel.domain.Account;
import ru.interosite.openbooker.datamodel.domain.AccountType;
import ru.interosite.openbooker.datamodel.domain.BaseEntity;
import ru.interosite.openbooker.datamodel.domain.EntitiesFactory;
import ru.interosite.openbooker.datamodel.domain.Funds;
import ru.interosite.openbooker.datamodel.tables.AccountBalanceTableModel;
import ru.interosite.openbooker.datamodel.tables.AccountsTableModel;
import ru.interosite.openbooker.datamodel.tables.TableModel;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

public class AccountGateway extends DatabaseGateway {

	public AccountGateway(DBAccess dba) {
		super(dba);
	}

	@Override
	protected String getTableName() {
		return TableModel.getModel(AccountsTableModel.class).getTableName();
	}

	@Override
	protected String[] getColumns() {
		return new String[]{AccountsTableModel.TITLE, AccountsTableModel.TYPE_ID};
	}
	
	@Override
	protected ContentValues getContentValues(BaseEntity entity) {
		if(!(entity instanceof Account)) {
			throw new IllegalArgumentException();
		}
		
		Account acc = (Account)entity;
		
		ContentValues values = new ContentValues();
		values.put("title", acc.getTitle());
		values.put("type_id", acc.getType().getId());
		
		return values;
	}
	
	@Override
	protected BaseEntity loadEntity(long id, Cursor c) {
		int titleIndex = c.getColumnIndex(AccountsTableModel.TITLE);
		int typeIdIndex = c.getColumnIndex(AccountsTableModel.TYPE_ID);
		AccountType type = AccountType.valueOf(typeIdIndex);
		//TODO load funds
		Account acc = EntitiesFactory.createAccount(type, Funds.EMPTY);
		List<Funds> fundsList = loadFunds(id);
		return null;
	}
	
	protected List<Funds> loadFunds(long id) {
		List<Funds> funds = new ArrayList<Funds>();
		SQLiteDatabase db = mDba.getReadableDatabase();
		String balanceTableName = TableModel.getModel(AccountBalanceTableModel.class).getTableName();
		String[] columns = new String[] {
			AccountBalanceTableModel.CURRENCY_CODE,
			AccountBalanceTableModel.VALUE
		};
		Cursor c = db.query(balanceTableName, columns, AccountBalanceTableModel.ACCOUNT_ID + "=?", new String[] {String.valueOf(id)}, AccountBalanceTableModel.CURRENCY_CODE, null, null);
		if(c!=null && c.getCount() > 0) {
			while(c.moveToNext()) {
				String currencyCode = c.getString(c.getColumnIndex(AccountBalanceTableModel.CURRENCY_CODE));
				if(TextUtils.isEmpty(currencyCode)) {
					throw new RuntimeException("Currency code is empty in AccountBalance table");
				}
				
				Currency currency = null;
				try {
					currency = Currency.getInstance(currencyCode);
				}
				catch(IllegalArgumentException e) {
					throw new RuntimeException("Currency code is invalid (" + currencyCode + ")");
				}
				
				long value = c.getLong(c.getColumnIndex(AccountBalanceTableModel.VALUE));								
				funds.add(new Funds(value, currency));				
			}
		}
		
		return funds;
	}
	
}