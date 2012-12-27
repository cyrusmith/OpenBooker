package ru.interosite.openbooker.domains.funds;

public class IllegalOperation extends RuntimeException {
	private static final long serialVersionUID = -1102999784550998658L;
	public IllegalOperation(String message) {
		super(message);
	}
}