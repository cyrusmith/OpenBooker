package ru.interosite.openbooker.datamodel.tables;

public class AccountsTableModel extends TableModel {
	
	public static final String TITLE = "title";
	public static final String TYPE_ID = "type_id";
	
	AccountsTableModel() {
		setTableName("Accounts");
		
		addColumn(TITLE, "TEXT NOT NULL");
		addColumn(TYPE_ID, "INTEGER NOT NULL");
	}
	
}