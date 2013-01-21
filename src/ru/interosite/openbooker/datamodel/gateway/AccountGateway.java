package ru.interosite.openbooker.datamodel.gateway;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import ru.interosite.openbooker.datamodel.domain.Account;
import ru.interosite.openbooker.datamodel.domain.AccountType;
import ru.interosite.openbooker.datamodel.domain.BaseEntity;
import ru.interosite.openbooker.datamodel.domain.Funds;
import ru.interosite.openbooker.datamodel.tables.AccountBalanceTableModel;
import ru.interosite.openbooker.datamodel.tables.AccountsTableModel;
import ru.interosite.openbooker.datamodel.tables.TableModel;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

public class AccountGateway extends DatabaseGateway {

	@Override
	protected Class<? extends BaseEntity> getEntityClass() {
		return Account.class;
	}
	
	@Override
	protected ContentValues doGetContentValues(BaseEntity entity) {
		
		Account acc = (Account)entity;
		
		ContentValues values = new ContentValues();
		values.put(AccountsTableModel.TITLE, acc.getTitle());
		values.put(AccountsTableModel.TYPE_ID, acc.getType().getId());
		
		return values;
	}
	
	@Override
	protected BaseEntity loadEntity(long id, Cursor c) {
		String title = c.getString(c.getColumnIndex(AccountsTableModel.TITLE));
		int typeId = c.getInt(c.getColumnIndex(AccountsTableModel.TYPE_ID));
		AccountType type = AccountType.valueOf(typeId);
		Account acc = mEntitiesFactory.createAccount(type, title, Funds.EMPTY);
		acc.setId(id);
		List<Funds> fundsList = loadFunds(id);
		for(Funds funds : fundsList) {
			acc.addFunds(funds);	
		}		
		return acc;
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

	@Override
	protected TableModel getTableModel() {
		return TableModel.getModel(AccountsTableModel.class);
	}
	
}