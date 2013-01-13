package ru.interosite.openbooker.datamodel.tables;

public class Operation extends TableModel {
	
	public static final String DATETIME = "created";
	public static final String TYPE_ID = "operation_type_id";
	
	Operation() {
		mTableName = "Operations";
		
		mNameTypeMap.put(DATETIME, "INTEGER NOT NULL");
		mNameTypeMap.put(TYPE_ID, "INTEGER NOT NULL");
	}

}