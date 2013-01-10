package ru.interosite.openbooker.datamodel.domain;

import java.util.Map;

public class IncomeSource extends BaseEntity {
	private String mTitle = null;
	
	public IncomeSource(String title) {
		if(title==null) {
			throw new IllegalArgumentException("Title is null");
		}
		mTitle = title;
	}
	
	public String getTitle() {
		return mTitle;
	}
	
}