package ru.interosite.openbooker.domains.funds;

public enum Currency {

	RUR("RUR"), 
	USD("USD");
	
	private final String mStringRep;
	private Currency(String stringRep) {
		mStringRep = stringRep;
	}
	
	public String getStringRep() {
		return mStringRep;
	}
	
	/*package*/static long convert(long value, Currency from, Currency to) {
		//TODO
		return value;
	}
	
}