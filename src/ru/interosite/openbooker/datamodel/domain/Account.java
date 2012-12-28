package ru.interosite.openbooker.datamodel.domain;

public class Account {

	Account(AccountType type) {}
	
	public void refill(Funds funds, IncomeSource source) {}	
	
	public void debit(Funds funds, ExpenseType type) {}
	
	public void moveTo(Account account, Funds funds) {}
	
}