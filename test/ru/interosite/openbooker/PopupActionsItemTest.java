package ru.interosite.openbooker;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Context;
import android.content.res.Resources;
import static junit.framework.Assert.assertTrue;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class PopupActionsItemTest {

	public static final int ACTION_INCOME = 1;
	public static final int ACTION_EXPENSE = 2;
	public static final int ACTION_MOVE = 3;
	
	private Context mContext = null;
	private Resources mResources = null;

	@Before
	public void setUp() {		
		mContext = Robolectric.application.getApplicationContext();
		mResources = mContext.getResources();
	}
	
	@Test(expected=IllegalArgumentException.class)	
	public void noDuplicateIds() {
		PopupActionItem actionIncome1 = new PopupActionItem(ACTION_INCOME,
				mContext.getString(R.string.add_income), mResources.getDrawable(R.drawable.ic_action_income));
		
		PopupActionItem actionIncome2 = new PopupActionItem(ACTION_INCOME,
				mContext.getString(R.string.add_expense), mResources.getDrawable(R.drawable.ic_action_expense));		
		
		PopupActionsWidget widget = new PopupActionsWidget(Robolectric.application.getApplicationContext());
		
		widget.addItem(actionIncome1);
		widget.addItem(actionIncome2);
	}
	
	@Test
	public void configureActionsPopup() {
		
		final PopupActionItem actionIncome = new PopupActionItem(ACTION_INCOME,
				mContext.getString(R.string.add_income), mResources.getDrawable(R.drawable.ic_action_income));
		
		final PopupActionItem actionExpense = new PopupActionItem(ACTION_EXPENSE,
				mContext.getString(R.string.add_expense),
				mResources.getDrawable(R.drawable.ic_action_expense));
		
		final PopupActionItem actionMove = new PopupActionItem(ACTION_MOVE,
				mContext.getString(R.string.add_move), mResources.getDrawable(R.drawable.ic_action_move));
		
		PopupActionsWidget widget = new PopupActionsWidget(Robolectric.application.getApplicationContext());
		widget.addItem(actionIncome);
		widget.addItem(actionExpense);
		widget.addItem(actionMove);
		
		widget.setItemSelectedListener(new PopupActionsWidget.IItemSelectListener() {
			public void onItemSelected(int actionId) {
				assertTrue(actionId==ACTION_MOVE);
			}
		});
		
		new MethodInvoker(widget).call("fireClick", int.class).with(actionMove.getId());
		
	}
	
}