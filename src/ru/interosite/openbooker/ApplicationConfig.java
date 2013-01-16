package ru.interosite.openbooker;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.text.TextUtils;

public class ApplicationConfig {
	
	public static final String DB_NAME = "openbooker";
	private static final ApplicationConfig mInstance = new ApplicationConfig();
	
	private volatile Context mContext = null;
	
	public static final ApplicationConfig getInstance() {
		return mInstance;
	}
	
	private static final Map<String, Integer> mStringsMap = new HashMap<String, Integer>();
	
	public void init(Context context) {
		if(context==null) {
			throw new IllegalArgumentException("Context is null");
		}
		if(mContext==null) {
			synchronized (this) {
				if(mContext==null) {
					mContext = context;
				}
			}
		}
	}
	
	public int getVersionCode() {
		try {
			return mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionCode;
		}
		catch(Exception e) {
			throw new RuntimeException("Cannot read version code: " + e.getMessage());
		}
	}
	
	public String getString(String code) {
		Integer value = mStringsMap.get(code);
		String result = code;
		if(value!=null) {
			String resString = mContext.getString(value.intValue());
			if(!TextUtils.isEmpty(resString)) {
				result = resString;
			}
		}
		return result;
	}
	
}