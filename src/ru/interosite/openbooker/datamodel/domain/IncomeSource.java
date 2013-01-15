package ru.interosite.openbooker.datamodel.domain;


public class IncomeSource extends BaseEntity {
	private String mTitle = null;
	
	IncomeSource(String title) {
		if(title==null || "".equals(title)) {
			throw new IllegalArgumentException("Title is empty");
		}
		mTitle = title;
	}
	
	public String getTitle() {
		return mTitle;
	}
	
}