package ru.interosite.openbooker;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Context;
import android.content.res.Resources;

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

	@Test
	public void testAction() {
		PopupActionItem actionIncome = new PopupActionItem(ACTION_INCOME,
				mContext.getString(R.string.add_income), mResources.getDrawable(R.drawable.ic_action_income));
		PopupActionItem actionExpense = new PopupActionItem(ACTION_EXPENSE,
				mContext.getString(R.string.add_expense),
				mResources.getDrawable(R.drawable.ic_action_expense));
		PopupActionItem actionMove = new PopupActionItem(ACTION_MOVE,
				mContext.getString(R.string.add_move), mResources.getDrawable(R.drawable.ic_action_move));
	}

}