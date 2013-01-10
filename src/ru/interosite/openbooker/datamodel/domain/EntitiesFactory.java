package ru.interosite.openbooker.datamodel.domain;

public class EntitiesFactory {
	public static Account createAccount(AccountType type) {
		return new Account(type);
	}
}