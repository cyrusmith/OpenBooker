package ru.interosite.openbooker.datamodel.gateway;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;
import ru.interosite.openbooker.datamodel.DBAccess;
import ru.interosite.openbooker.datamodel.domain.Account;
import ru.interosite.openbooker.datamodel.domain.AccountType;
import ru.interosite.openbooker.datamodel.domain.BaseEntity;
import ru.interosite.openbooker.datamodel.domain.EntitiesFactory;
import ru.interosite.openbooker.datamodel.domain.Funds;

public class AccountGateway extends DatabaseGateway {

	public static final String TABLE_NAME = "accounts";
	public static final String TITLE = "title";
	public static final String TYPE_ID = "type_id";
	
	public static final String CREATE_TABLE_SQL = "CREATE TABLE "
			+ AccountGateway.TABLE_NAME + " (" + BaseColumns._ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " 
			+ "title REAL, "
			+ "type_id INTEGER);";		
	
	public AccountGateway(DBAccess dba) {
		super(dba);
	}

	@Override
	protected String getTableName() {
		return TABLE_NAME;
	}

	@Override
	protected String[] getColumns() {
		return new String[]{TITLE, TYPE_ID};
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
		int titleIndex = c.getColumnIndex(TITLE);
		int typeIdIndex = c.getColumnIndex(TYPE_ID);
		AccountType type = AccountType.valueOf(typeIdIndex);
		//TODO load funds
		Account acc = EntitiesFactory.createAccount(type, Funds.EMPTY);
		List<Funds> fundsList = loadFunds(id);
		return null;
	}
	
	protected List<Funds> loadFunds(long id) {
		List<Funds> funds = new ArrayList<Funds>();
		//TODO load funds
		Make db schema stupid!
		return funds;
	}
	
}