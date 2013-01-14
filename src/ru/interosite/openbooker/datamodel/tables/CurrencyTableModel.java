package ru.interosite.openbooker.datamodel.tables;

public class CurrencyTableModel extends TableModel {
	
	public static final String CODE = "code";
	public static final String DESCRIPTION = "description";
	
	CurrencyTableModel() {
		mTableName = "Currencies";		
		mNameTypeMap.put(CODE, "TEXT NOT NULL");		
		setCompoundKey(CODE);
	}
}