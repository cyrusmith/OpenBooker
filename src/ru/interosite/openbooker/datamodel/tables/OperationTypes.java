package ru.interosite.openbooker.datamodel.tables;

public class OperationTypes extends TableModel {
	
	public static final String NAME = "name";
	
	OperationTypes() {
		mTableName = "OperationTypes";
		
		mNameTypeMap.put(NAME, "TEXT NOT NULL");		
	}

}