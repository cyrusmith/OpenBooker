package ru.interosite.openbooker.datamodel.domain;

public class EntitiesFactory {
	public static Account createAccount(AccountType type, Funds initialBalance) {
		if(type==null) {
			throw new IllegalArgumentException("AccountType is null");
		}
		if(initialBalance==null) {
			throw new IllegalArgumentException("Account's balance is null");
		}
		return new Account(type, initialBalance);
	}
	
}