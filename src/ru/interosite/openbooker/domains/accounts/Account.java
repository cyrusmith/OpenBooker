package ru.interosite.openbooker.domains.accounts;

import ru.interosite.openbooker.domains.funds.Funds;

public class Account {

	public void refill(Funds funds, IncomeSource source) {}	
	
	public void debit(Funds funds, ExpenseType type) {}
	
	public void moveTo(Account account, Funds funds) {}
	
}