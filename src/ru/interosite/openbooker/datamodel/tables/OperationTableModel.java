package ru.interosite.openbooker.datamodel.tables;

public class OperationTableModel extends TableModel {
	
	public static final String DATETIME = "created";
	public static final String TYPE_ID = "operation_type_id";
	
	OperationTableModel() {
		setTableName("Operations");
		
		addColumn(DATETIME, "INTEGER NOT NULL");
		addColumn(TYPE_ID, "INTEGER NOT NULL");
	}

}