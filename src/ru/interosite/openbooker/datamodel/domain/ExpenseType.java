package ru.interosite.openbooker.datamodel.domain;

import ru.interosite.openbooker.ApplicationConfig;

public class ExpenseType extends BaseEntity {
	
	public static final ExpenseType ROOT = new ExpenseType();
	
	private String mTitle = null;
	
	private ExpenseType mParent = ROOT;
	
	ExpenseType() {
		mTitle = ApplicationConfig.getInstance().getString(BaseEntity.UNTITLED);
	}
	
	public void setTitle(String title) {
		if(title==null || "".equals(title)) {
			title = ApplicationConfig.getInstance().getString(BaseEntity.UNTITLED);
		}
		mTitle = title;
	}
	
	public String getTitle() {
		return mTitle;
	}
	
	public void setParent(ExpenseType parent) {
		if(parent==null) {
			throw new IllegalArgumentException("Cannot set null parent. Use ROOT instead.");
		}
		
		if(parent!=ROOT) {
			if(parent.getId()==null) {
				throw new IllegalArgumentException("Cannot set parent with null id. Save to database first.");
			}
		}
		mParent = parent;
	}
	
	public ExpenseType getParent() {
		return mParent;
	}

}