package ru.interosite.openbooker.datamodel.domain;

public class UnsufficientFundsException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public UnsufficientFundsException(String message) {
		super(message);
	}
	
}