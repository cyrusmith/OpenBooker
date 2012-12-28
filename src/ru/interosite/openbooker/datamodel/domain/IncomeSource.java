package ru.interosite.openbooker.datamodel.domain;

public class IncomeSource {
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