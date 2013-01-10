package ru.interosite.openbooker;

import android.content.Context;

public class ApplicationConfig {
	
	public static final String DB_NAME = "openbooker";
	
	public static int getVersionCode(Context context) {
		try {
			return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
		}
		catch(Exception e) {
			throw new RuntimeException("Cannot read version code: " + e.getMessage());
		}
	}
	
}