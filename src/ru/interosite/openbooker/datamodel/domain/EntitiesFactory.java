package ru.interosite.openbooker.datamodel.domain;

public class EntitiesFactory {
	
	public static OperationRefill createRefillOperation(Account account, Funds funds) {
		return new OperationRefill(account, funds);
	}
	
	public static Account createAccount(AccountType type, Funds initialFunds) {
		Account acc = new Account(type);
		acc.addFunds(initialFunds);
		return acc;
	}
	
	public static IncomeSource createIncomeSource(String title) {
		if(title==null || "".equals(title)) {
			title = BaseEntity.UNTITLED;
		}
		IncomeSource source = new IncomeSource(title);
		return source;
	}
	
}