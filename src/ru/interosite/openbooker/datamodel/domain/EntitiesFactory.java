package ru.interosite.openbooker.datamodel.domain;

import ru.interosite.openbooker.ApplicationConfig;

public class EntitiesFactory {
	
	public EntitiesFactory() {}
	
	public Operation createOperation(Operation.OperationType type) {
		return Operation.newInstance(type);
	}
	
	public Account createAccount(AccountType type, Funds initialFunds) {
		Account acc = new Account(type);
		acc.addFunds(initialFunds);
		return acc;
	}
	
	public  IncomeSource createIncomeSource(String title) {
		if(title==null || "".equals(title)) {
			title = ApplicationConfig.getInstance().getString(BaseEntity.UNTITLED);
		}
		IncomeSource source = new IncomeSource(title);
		return source;
	}
	
}