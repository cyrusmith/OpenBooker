package ru.interosite.openbooker.datamodel.tables;

public class AccountBalanceTableModel extends TableModel {
	
	public static final String ACCOUNT_ID = "account_id";
	public static final String CURRENCY_ID = "currency_id";
	public static final String VALUE = "value";
	
	AccountBalanceTableModel() {
		mTableName = "AccountBalance";
		
		mNameTypeMap.put(ACCOUNT_ID, "INTEGER NOT NULL");
		mNameTypeMap.put(CURRENCY_ID, "INTEGER NOT NULL");
		mNameTypeMap.put(VALUE, "INTEGER NOT NULL");
	}

}