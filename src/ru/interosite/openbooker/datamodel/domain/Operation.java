package ru.interosite.openbooker.datamodel.domain;

import java.util.Map;

public class Operation extends BaseEntity {
	
	protected final Account mAccount;
	
	Operation(Account parentAccount) {
		if(parentAccount==null) {
			throw new IllegalArgumentException("Parent account not set for Operation");
		}
		mAccount = parentAccount;
	}
	
	public void execute() {
		//TODO make dirty
	}

	@Override
	public Map<String, String> getValuesMap() {
		// TODO Auto-generated method stub
		return null;
	}
	
}