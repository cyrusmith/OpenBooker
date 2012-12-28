package ru.interosite.openbooker.datamodel.domain;

public class ExpenseType {

	private String mTitle = null;

	public ExpenseType() {
	}

	public ExpenseType(String title) {
		mTitle = title;
	}
	
	public String getTitle() {
		return mTitle;
	}
}