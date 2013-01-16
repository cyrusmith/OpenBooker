package ru.interosite.openbooker.datamodel.domain;

import ru.interosite.openbooker.ApplicationConfig;

public class EntitiesFactory {
	
	public static Operation createOperation(Account account, Funds funds) {
		return new OperationRefill(account, funds);
	}
	
	public static Account createAccount(AccountType type, Funds initialFunds) {
		Account acc = new Account(type);
		acc.addFunds(initialFunds);
		return acc;
	}
	
	public static IncomeSource createIncomeSource(String title) {
		if(title==null || "".equals(title)) {
			title = ApplicationConfig.getInstance().getString(BaseEntity.UNTITLED);
		}
		IncomeSource source = new IncomeSource(title);
		return source;
	}
	
}