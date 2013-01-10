package ru.interosite.openbooker.datamodel.domain;

public class Account {

	Account(AccountType type) {}
	
	void refill(Funds funds, IncomeSource source) {}	
	
	void debit(Funds funds, ExpenseType type) {}
	
}