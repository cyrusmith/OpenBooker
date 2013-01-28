package ru.interosite.openbooker.datamodel.domain;

import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

import ru.interosite.openbooker.ApplicationConfig;

public class Account extends BaseEntity {
	
	private Map<Currency, Funds> mFunds = new HashMap<Currency, Funds>();
	
	private String mTitle = null;
	private AccountType mType = AccountType.UNKNOWN;		
	
	Account(AccountType type) {
		if(type==null) {
			throw new IllegalArgumentException("Type not set");
		}
		mType = type;
		mTitle = ApplicationConfig.getInstance().getString(BaseEntity.UNTITLED);
	}
	
	public void addFunds(Funds funds) {
		if(mFunds.containsKey(funds.getCurrency())) {
			funds = funds.plus(mFunds.get(funds.getCurrency()));
		}
		mFunds.put(funds.getCurrency(), funds);
	}
	
	public void setTitle(String title) {
		if(title==null || "".equals(title)) {
			title = ApplicationConfig.getInstance().getString(BaseEntity.UNTITLED);
		}
		mTitle = title;
	}
	
	public String getTitle() {
		return mTitle;
	}
	
	public void setType(AccountType type) {
		mType = type;
	}
	
	public AccountType getType() {
		return mType;
	}
	
	public Map<Currency, Funds> getFunds() {
		return mFunds;
	}
	
	public boolean hasSufficientFunds(Funds funds) {		
		if(funds==null) {
			throw new IllegalArgumentException("Funds is null");
		}
		if(funds==Funds.EMPTY) {
			return true;
		}
		Currency currency = funds.getCurrency();
		Funds accFunds = mFunds.get(currency);
		if(accFunds!=null) {
			 return accFunds.getValue()>=funds.getValue();
		}
		return false;
	}
	
}