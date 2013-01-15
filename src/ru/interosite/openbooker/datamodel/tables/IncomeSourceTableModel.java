package ru.interosite.openbooker.datamodel.tables;

public class IncomeSourceTableModel extends TableModel {
	
	public static final String TITLE = "title";
	
	IncomeSourceTableModel() {
		setTableName("IncomeSources");
		addColumn(TITLE, "TEXT NOT NULL");
	}
	
}