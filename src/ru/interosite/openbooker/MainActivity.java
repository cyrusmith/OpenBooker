package ru.interosite.openbooker;

import org.holoeverywhere.app.Activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.ActionMode.Callback;

public class MainActivity extends Activity {
	
	private AccountsFragment mAccountsFragment;
	private BalanceFragment mBalanceFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.main);
				
		mAccountsFragment = new AccountsFragment();
		mBalanceFragment = new BalanceFragment();
		
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.add(R.id.accounts_list, mAccountsFragment);
		transaction.add(R.id.balance, mBalanceFragment);
		transaction.addToBackStack(null);
		transaction.commit();
		
	}

	@Override
	public ActionBar getSupportActionBar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSupportProgress(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSupportProgressBarIndeterminate(boolean arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSupportProgressBarIndeterminateVisibility(boolean arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSupportProgressBarVisibility(boolean arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSupportSecondaryProgress(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ActionMode startActionMode(Callback arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onActionModeStarted(ActionMode mode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onActionModeFinished(ActionMode mode) {
		// TODO Auto-generated method stub
		
	}
	
}