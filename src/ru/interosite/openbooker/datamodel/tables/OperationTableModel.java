package ru.interosite.openbooker.datamodel.tables;

public class OperationTableModel extends TableModel {
	
	public static final String CURRENCY_CODE = "currency_code";
	public static final String VALUE = "value";	
	public static final String DATETIME = "created";
	public static final String TYPE = "operation_type";
	public static final String OPERATION_DATA = "operation_data";
	
	OperationTableModel() {
		
		setTableName("Operations");
		
		addColumn(CURRENCY_CODE, "TEXT NOT NULL");
		addColumn(VALUE, "INTEGER NOT NULL");
		addColumn(DATETIME, "INTEGER NOT NULL");
		addColumn(TYPE, "TEXT NOT NULL");
		addColumn(OPERATION_DATA, "TEXT NOT NULL");
		
	}

}