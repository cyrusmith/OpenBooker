package ru.interosite.openbooker;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class PopupActionsWidget {

	private PopupWindow mWindow;
	private Context mContext;
	private WindowManager mWindowManager;
	private LayoutInflater mInflater;

	private View mRootView;

	private GridView mItemsContainer;

	private List<PopupActionItem> mItems = new ArrayList<PopupActionItem>();

	private IItemSelectListener mListener;

	private BaseAdapter mItemsAdapter = new BaseAdapter() {

		@Override
		public View getView(int pos, View convertView, ViewGroup parent) {
			View itemsView;
			PopupActionItem item = mItems.get(pos);
			if (convertView == null) {
				itemsView = mInflater.inflate(R.layout.popup_action_item, null);
				ImageView icon = (ImageView) itemsView
						.findViewById(R.id.item_icon);
				TextView title = (TextView) itemsView
						.findViewById(R.id.item_title);
				icon.setImageDrawable(item.getIcon());
				title.setText(item.getTitle());
				
				final int itemId = item.getId();
				itemsView.setOnClickListener(new OnClickListener() {					
					@Override
					public void onClick(View item) {
						fireClick(itemId);
						mWindow.dismiss();
					}
				});
			} else {
				itemsView = convertView;
			}

			return itemsView;
		}

		@Override
		public long getItemId(int pos) {
			return mItems.get(pos).getId();
		}

		@Override
		public Object getItem(int pos) {
			return mItems.get(pos);
		}

		@Override
		public int getCount() {
			return mItems.size();
		}
	};

	public PopupActionsWidget(Context context) {
		mContext = context;
		mWindow = new PopupWindow(context);
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		mWindow.setTouchInterceptor(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					mWindow.dismiss();
					return true;
				}
				return false;
			}
		});

		mWindowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		mRootView = mInflater.inflate(R.layout.popup_actions, null);
		mItemsContainer = (GridView) mRootView
				.findViewById(R.id.popup_action_container);
		mItemsContainer.setAdapter(mItemsAdapter);
		mWindow.setContentView(mRootView);
	}

	public void show(View parentView) {

		mWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
		mWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
		mWindow.setTouchable(true);
		mWindow.setFocusable(true);
		mWindow.setOutsideTouchable(true);

		int[] location = new int[2];

		parentView.getLocationOnScreen(location);

		Rect anchorRect = new Rect(location[0], location[1], location[0]
				+ parentView.getWidth(), location[1] + parentView.getHeight());

		mRootView.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		int rootWidth = mRootView.getMeasuredWidth();
		int rootHeight = mRootView.getMeasuredHeight();

		int screenWidth = mWindowManager.getDefaultDisplay().getWidth();

		int xPos = (screenWidth - rootWidth) / 2;
		int yPos = anchorRect.top - rootHeight;

		if (rootHeight > parentView.getTop()) {
			yPos = anchorRect.bottom;
		}

		mWindow.showAtLocation(parentView, Gravity.NO_GRAVITY, xPos, yPos);

	}
	
	public void addItem(PopupActionItem item) {
		if (item == null) {
			throw new NullPointerException("PopupActionItem is null");
		}
		for(PopupActionItem addedItem : mItems) {
			if(item.getId()==addedItem.getId()) {
				throw new IllegalArgumentException("Item with id " + addedItem.getId() + " had already been added");
			}
		}
		mItems.add(item);
		mItemsAdapter.notifyDataSetChanged();
	}

	public void setItemSelectedListener(IItemSelectListener listener) {
		if (listener == null) {
			throw new NullPointerException();
		}
		mListener = listener;
	}

	private void fireClick(int id) {
		if (mListener != null) {
			mListener.onItemSelected(id);
		}
	}

	public interface IItemSelectListener {
		public void onItemSelected(int itemId);
	}

}