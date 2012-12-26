package ru.interosite.openbooker;

import java.util.LinkedHashSet;

public class PopupActionsWidget {

	private LinkedHashSet<PopupActionItem> mItems = new LinkedHashSet<PopupActionItem>();
	
	private IItemSelectListener mListener;
	
	public void addItem(PopupActionItem item) {
		if(item==null) {
			throw new NullPointerException("PopupActionItem is null");
		}
		mItems.add(item);
	}
	
	public void setItemSelectedListener(IItemSelectListener listener) {
		if(listener==null) {
			throw new NullPointerException();
		}
		mListener = listener;
	}
	
	public interface IItemSelectListener {
		public void onItemSelected(int itemId);
	}
	
	private void fireClick(int id) {
		mListener.onItemSelected(id);
	}
	
}