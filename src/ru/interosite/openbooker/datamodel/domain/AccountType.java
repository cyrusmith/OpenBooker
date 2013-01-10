package ru.interosite.openbooker.datamodel.domain;

/**
 * @type value object
 */
public enum AccountType {

	UNKNOWN(0),
	CASH(1), 
	CREDIT_CARD(2), 
	DEBIT_CARD(3), 
	WEBMONEY(4);

	private final int mCode;

	private AccountType(int code) {
		mCode = code;
	}
}