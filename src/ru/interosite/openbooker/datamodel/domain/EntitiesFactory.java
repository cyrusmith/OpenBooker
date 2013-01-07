package ru.interosite.openbooker.datamodel.domain;

public class EntitiesFactory {
	public static Account createAccount(AccountType type, Funds initialBalance) {
		if(type==null) {
			throw new IllegalArgumentException("AccountType is null");
		}
		if(initialBalance==null) {
			throw new IllegalArgumentException("Account's balance is null");
		}
		Account account = new Account(type, initialBalance);
		account.setNew();
		return account;
	}
	
}