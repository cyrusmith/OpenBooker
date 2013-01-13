package ru.interosite.openbooker.datamodel.tables;

public class AccountsTableModel extends TableModel {
	
	public static final String TITLE = "title";
	public static final String TYPE_ID = "type_id";
	
	AccountsTableModel() {
		mTableName = "Accounts";
		
		mNameTypeMap.put(TITLE, "TEXT NOT NULL");
		mNameTypeMap.put(TYPE_ID, "INTEGER NOT NULL");
	}
	
}