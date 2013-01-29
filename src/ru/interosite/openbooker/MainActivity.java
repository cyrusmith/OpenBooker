package ru.interosite.openbooker;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class MainActivity extends FragmentActivity {
	
	private AccountsFragment mAccountsFragment;
	private BalanceFragment mBalanceFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.main);
				
		/*mAccountsFragment = new AccountsFragment();
		mBalanceFragment = new BalanceFragment();
		
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.add(R.id.accounts_list, mAccountsFragment);
		transaction.add(R.id.balance, mBalanceFragment);
		transaction.addToBackStack(null);
		transaction.commit();*/
		
	}
	
}