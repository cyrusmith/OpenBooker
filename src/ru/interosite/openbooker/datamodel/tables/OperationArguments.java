package ru.interosite.openbooker.datamodel.tables;


public class OperationArguments extends TableModel {
	
	public static final String OPERATION_ID = "operation_id";
	public static final String NAME = "name";
	public static final String VALUE = "value";
	
	OperationArguments() {
		setTableName("OperationArguments");
		
		addColumn(OPERATION_ID, "INTEGER NOT NULL");
		addColumn(NAME, "TEXT NOT NULL");
		addColumn(VALUE, "TEXT NOT NULL");
		
	}

}