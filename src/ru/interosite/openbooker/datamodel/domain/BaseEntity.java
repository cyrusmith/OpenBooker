package ru.interosite.openbooker.datamodel.domain;

public abstract class BaseEntity {
	
	public static final String UNTITLED = "untitled";
	
	public static final BaseEntity UNKNOWN_ENTITY = new UnknownEntity();
	
	private Long mId = null;
	
	BaseEntity() {}
	
	public void setId(Long id) {
		if(id==null) {
			throw new IllegalArgumentException("Id is null");
		}
		mId = id;
	}
	
	public Long getId() {
		return mId;
	}
	
	public static boolean isUnknown(BaseEntity entity) {
		return entity instanceof UnknownEntity;
	}
	
}