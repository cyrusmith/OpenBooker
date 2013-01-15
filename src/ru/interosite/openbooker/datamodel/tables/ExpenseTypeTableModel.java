package ru.interosite.openbooker.datamodel.tables;

public class ExpenseTypeTableModel extends TableModel {
	
	public static final String TITLE = "title"; 
	public static final String PARENT_TYPE_ID = "parent_id"; 
	
	ExpenseTypeTableModel() {		
		setTableName("ExpenseTypes");
		
		addColumn(TITLE, "TEXT NOT NULL");
		addColumn(PARENT_TYPE_ID, "INTEGER NOT NULL");
	}
	
}