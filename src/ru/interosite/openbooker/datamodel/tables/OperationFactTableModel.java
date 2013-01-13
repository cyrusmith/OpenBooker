package ru.interosite.openbooker.datamodel.tables;

public class OperationFactTableModel extends TableModel {
	
	public static final String CURRENCY_ID = "currency_id";
	public static final String OPERATION_ID = "operation_id";
	public static final String OPERATION_TYPE_ID = "operation_type_id";
	public static final String CATEGORY_ID = "category_id";
	public static final String VALUE = "value";
	
	OperationFactTableModel() {
		mTableName = "OperationFacts";
		
		mNameTypeMap.put(CURRENCY_ID, "INTEGER NOT NULL");
		mNameTypeMap.put(OPERATION_ID, "INTEGER NOT NULL");
		mNameTypeMap.put(OPERATION_TYPE_ID, "INTEGER NOT NULL");
		mNameTypeMap.put(CATEGORY_ID, "INTEGER NOT NULL");
		mNameTypeMap.put(VALUE, "INTEGER NOT NULL");
		
		setCompoundKey(CURRENCY_ID, OPERATION_ID, OPERATION_TYPE_ID, CATEGORY_ID);
	}

}