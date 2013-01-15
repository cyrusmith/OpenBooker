package ru.interosite.openbooker.datamodel.tables;

public class OperationFactTableModel extends TableModel {
	
	public static final String CURRENCY_CODE = "currency_code";
	public static final String OPERATION_ID = "operation_id";
	public static final String OPERATION_TYPE_ID = "operation_type_id";
	public static final String CATEGORY_ID = "category_id";
	public static final String VALUE = "value";
	
	OperationFactTableModel() {
		setTableName("OperationFacts");
		
		addColumn(CURRENCY_CODE, "TEXT NOT NULL");
		addColumn(OPERATION_ID, "INTEGER NOT NULL");
		addColumn(OPERATION_TYPE_ID, "INTEGER NOT NULL");
		addColumn(CATEGORY_ID, "INTEGER NOT NULL");
		addColumn(VALUE, "INTEGER NOT NULL");
		
		setCompoundKey(CURRENCY_CODE, OPERATION_ID, OPERATION_TYPE_ID, CATEGORY_ID);
	}

}