package ru.interosite.openbooker.datamodel.gateway;

import android.content.ContentValues;
import android.provider.BaseColumns;
import ru.interosite.openbooker.datamodel.DBAccess;
import ru.interosite.openbooker.datamodel.domain.Account;
import ru.interosite.openbooker.datamodel.domain.BaseEntity;

public class AccountGateway extends DatabaseGateway {

	public static final String TABLE_NAME = "accounts";
	
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
		return new String[]{"title", "type_id"};
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
	
}