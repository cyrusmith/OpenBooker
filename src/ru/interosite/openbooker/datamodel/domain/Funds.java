package ru.interosite.openbooker.datamodel.domain;

public class Funds {

	private final long mValue;
	private final Currency mCurrency;
	
	public Funds(long value, Currency currency) {
		mValue 		= value;
		mCurrency 	= currency;
	}
	
	public long getValue() {
		return mValue;
	}
	
	public Currency getCurrency() {
		return mCurrency;
	}
	
	public Funds plus(Funds funds) {
		
		if(funds==null) {
			return new Funds(mValue, mCurrency);
		}
		
		if(!mCurrency.equals(funds.getCurrency())) {
			throw new IllegalOperation("Currencies of operands are different");
		}
				
		return new Funds(mValue + funds.getValue(), getCurrency());
	}
	
	public Funds convertTo(Currency toCurrency) {
		
		if(toCurrency==null) {
			throw new IllegalArgumentException("Currency is null");
		}
		
		if(toCurrency.equals(getCurrency())) {
			return this;
		}
		
		long convertedValue = Currency.convert(getValue(), getCurrency(), toCurrency);
		
		return new Funds(convertedValue, toCurrency);
		
	}
	
	public Funds reverse() {
		return new Funds(-getValue(), getCurrency());
	}
	
}