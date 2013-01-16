package ru.interosite.openbooker.datamodel.tables;

public class OperationTableModel extends TableModel {
	
	public static final String DATETIME = "created";
	public static final String TYPE = "operation_type";
	public static final String OPERATION_DATA = "operation_data";
	
	OperationTableModel() {
		
		setTableName("Operations");
		
		addColumn(DATETIME, "INTEGER NOT NULL");
		addColumn(TYPE, "TEXT NOT NULL");
		addColumn(OPERATION_DATA, "TEXT NOT NULL");
		
	}

}