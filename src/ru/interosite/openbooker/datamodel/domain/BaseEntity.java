package ru.interosite.openbooker.datamodel.domain;

public class BaseEntity {
	
	private Long mId = null;
	
	public void setId(Long id) {
		if(id==null) {
			throw new IllegalArgumentException("Id is null");
		}
		mId = id;
	}
	
	public Long getId() {
		return mId;
	}
	
	public void setDirty() {
				
	}
	
	public void setClean() {
		
	}
	
}