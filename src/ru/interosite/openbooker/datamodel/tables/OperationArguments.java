package ru.interosite.openbooker.datamodel.tables;


public class OperationArguments extends TableModel {
	
	public static final String OPERATION_ID = "operation_id";
	public static final String NAME = "name";
	public static final String VALUE = "value";
	
	OperationArguments() {
		mTableName = "OperationArguments";
		
		mNameTypeMap.put(OPERATION_ID, "INTEGER NOT NULL");
		mNameTypeMap.put(NAME, "TEXT NOT NULL");
		mNameTypeMap.put(VALUE, "TEXT NOT NULL");
		
	}

}