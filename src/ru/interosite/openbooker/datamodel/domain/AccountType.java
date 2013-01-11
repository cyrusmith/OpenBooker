package ru.interosite.openbooker.datamodel.domain;

import android.util.SparseArray;

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

	private static final SparseArray<AccountType> mCodeTypeMap = new SparseArray<AccountType>();
	
	static {
		mCodeTypeMap.put(0, UNKNOWN);
		mCodeTypeMap.put(1, CASH);
		mCodeTypeMap.put(2, CREDIT_CARD);
		mCodeTypeMap.put(3, DEBIT_CARD);
		mCodeTypeMap.put(4, WEBMONEY);
	}
	
	private AccountType(int code) {
		mCode = code;
	}
	
	public int getId() {
		return mCode;
	}
	
	public static AccountType valueOf(int code) {
		AccountType type = mCodeTypeMap.get(code); 
		return type==null?UNKNOWN:type;
	}
}