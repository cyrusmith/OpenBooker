package ru.interosite.openbooker.datamodel.tables;

public class AccountBalanceTableModel extends TableModel {
	
	public static final String ACCOUNT_ID = "account_id";
	public static final String CURRENCY_CODE = "currency_code";
	public static final String VALUE = "value";
	
	AccountBalanceTableModel() {
		setTableName("AccountBalance");
		
		addColumn(ACCOUNT_ID, "INTEGER NOT NULL");
		addColumn(CURRENCY_CODE, "TEXT NOT NULL");
		addColumn(VALUE, "INTEGER NOT NULL");
		
		setCompoundKey(ACCOUNT_ID, CURRENCY_CODE);
	}

}