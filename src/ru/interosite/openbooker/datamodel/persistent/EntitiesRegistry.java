package ru.interosite.openbooker.datamodel.persistent;

import android.content.Context;

public enum EntitiesRegistry {
	INSTANCE;
	
	private volatile Context mContext = null;
	
	public static EntitiesRegistry getInstance() {
		return INSTANCE;
	}
	
	public void setContext(Context context) {
		mContext = context;
	}
}