package ru.interosite.openbooker.datamodel.domain;

public class EntitiesFactory {
	public static Account createAccount(AccountType type) {
		if(type==null) {
			throw new IllegalArgumentException("AccountType is null");
		}
		return new Account(type);
	}
}