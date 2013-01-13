package ru.interosite.openbooker.datamodel.tables;

import ru.interosite.openbooker.datamodel.domain.AccountType;


public class AccountTypeTableModel extends TableModel {
	
	public static final String NAME = "name";
	
	AccountTypeTableModel() {
		mTableName = "AccountTypes";

		mNameTypeMap.put(NAME, "TEXT NOT NULL");
		
		String format = "INSERT INTO %s ('id', 'title') VALUES (%s, \"%s\")";
		
		for(AccountType type : AccountType.values()) {			
			mInsertsOnCreate.add(String.format(format, getTableName(), type.getId(), type.toString()));	
		}
		
	}
	
}