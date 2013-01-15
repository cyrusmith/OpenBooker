package ru.interosite.openbooker.datamodel.tables;

public class OperationTypes extends TableModel {
	
	public static final String NAME = "name";
	
	OperationTypes() {
		setTableName("OperationTypes");
		
		addColumn(NAME, "TEXT NOT NULL");		
	}

}