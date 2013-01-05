package ru.interosite.openbooker.datamodel.persistent;

import android.content.Context;

public class ScriptsFactory {
	
	private static volatile ScriptsFactory mInstance = null;

	public static ScriptsFactory getInstance() {
		ScriptsFactory instance = mInstance;
		if (instance == null) {
			synchronized (ScriptsFactory.class) {
				instance = mInstance;
				if (instance == null) {
					mInstance = instance = new ScriptsFactory();
				}
			}
		}
		return instance;
	}
	
	private volatile Context mContext = null;
	
	private ScriptsFactory() {
	}
	
	public void setContext(Context context) {
		mContext = context;
	}
	
}