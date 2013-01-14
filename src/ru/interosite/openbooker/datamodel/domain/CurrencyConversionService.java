package ru.interosite.openbooker.datamodel.domain;

import java.util.Currency;

public class CurrencyConversionService {
	public static Funds convert(Funds from, Currency toCurrency) {
		//TODO load conversion map
		return new Funds(from.getValue()/2, toCurrency);
	}
}