package ru.interosite.openbooker.datamodel.domain;

import java.util.Map;

public abstract class BaseEntity {
	
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
	
	public abstract Map<String, String> getValuesMap();
	
}