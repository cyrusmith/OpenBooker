package ru.interosite.openbooker.datamodel.domain;

import java.util.Map;

public class ExpenseType extends BaseEntity {

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