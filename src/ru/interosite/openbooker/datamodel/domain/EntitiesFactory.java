package ru.interosite.openbooker.datamodel.domain;

import ru.interosite.openbooker.ApplicationConfig;

public class EntitiesFactory {
	
	public EntitiesFactory() {}
	
	public Operation createOperation(Operation.OperationType type) {
		return Operation.newInstance(type);
	}
	
	public Account createAccount(AccountType type, String title, Funds initialFunds) {
		Account acc = new Account(type);
		acc.setTitle(title);
		if(initialFunds != Funds.EMPTY) {
			acc.addFunds(initialFunds);
		}
		return acc;
	}
	
	public ExpenseType createExpenseType(String title, ExpenseType parent) {
		ExpenseType type = new ExpenseType();
		type.setTitle(title);
		if(parent==null) {
			parent = ExpenseType.ROOT;
		}		
		type.setParent(parent);
		return type;
	}
	
	public  IncomeSource createIncomeSource(String title) {
		if(title==null || "".equals(title)) {
			title = ApplicationConfig.getInstance().getString(BaseEntity.UNTITLED);
		}
		IncomeSource source = new IncomeSource(title);
		return source;
	}
	
}