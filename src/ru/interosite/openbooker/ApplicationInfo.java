package ru.interosite.openbooker;

import android.content.Context;

public class ApplicationInfo {
	
	public static int getVersionCode(Context context) {
		try {
			return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
		}
		catch(Exception e) {
			throw new RuntimeException("Cannot read version code: " + e.getMessage());
		}
	}
	
}