package ru.interosite.openbooker;

import android.graphics.drawable.Drawable;

public class PopupActionItem {

	private final int mId;
	private final String mTitle;
	private final Drawable mIcon;
	
	public PopupActionItem(int id, String title, Drawable icon) {
		mId = id;
		mTitle = title;
		mIcon = icon;
	}
	
	public int getId() {
		return mId;
	}
	
	public String getTitle() {
		return mTitle;
	}
	
	public Drawable getIcon() {
		return mIcon;
	}
	
}