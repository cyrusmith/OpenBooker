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
	
	public void setNew() {
		CompoundAction.getCurrentAction().addNew(this);	
	}
	
	public void setDirty() {
		CompoundAction.getCurrentAction().addDirty(this);	
	}
	
	public void setRemove() {
		CompoundAction.getCurrentAction().addRemove(this);	
	}
		
}