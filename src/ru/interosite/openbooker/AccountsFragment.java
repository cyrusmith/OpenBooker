package ru.interosite.openbooker;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Map;

import org.holoeverywhere.ArrayAdapter;
import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.ListFragment;
import org.holoeverywhere.widget.LinearLayout;
import org.holoeverywhere.widget.TextView;

import ru.interosite.openbooker.datamodel.DBAccess;
import ru.interosite.openbooker.datamodel.DomainRequestContext;
import ru.interosite.openbooker.datamodel.domain.Account;
import ru.interosite.openbooker.datamodel.domain.AccountType;
import ru.interosite.openbooker.datamodel.domain.Funds;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

public class AccountsFragment extends ListFragment {
	
	private class AccountsArrayAdapter extends ArrayAdapter<Account> {
		
		private int mResource;
		
		public AccountsArrayAdapter(Context context,
				int textViewResourceId, List<Account> accounts) {			
			super(context, textViewResourceId, accounts);	
			mResource = textViewResourceId;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			LinearLayout accView;
			
			Account acc = getItem(position);
			
			String title = acc.getTitle();
			Map<Currency, Funds> funds = acc.getFunds();
			AccountType type = acc.getType();
			
			if (convertView == null) {
				accView = new LinearLayout(getContext());
				LayoutInflater li = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				li.inflate(mResource, accView, true);				
			} else {
				accView = (LinearLayout) convertView;
			}
			
			TextView infoTV = (TextView)accView.findViewById(R.id.acc_info);
			TextView titleTV = (TextView)accView.findViewById(R.id.acc_title);
			TextView lastUpdatedTV = (TextView)accView.findViewById(R.id.acc_last_updated);

			infoTV.setText(title);
			titleTV.setText(title);
			lastUpdatedTV.setText(title);
			
			return accView;
		}
		
	}
	
	private class LoadAccountsTask extends AsyncTask<Void, Void, List<Account>> {

		@Override
		protected List<Account> doInBackground(Void... params) {
			DomainRequestContext.create(mDba);
			return DomainRequestContext.getInstance().getGatewayRegistry().get(Account.class).findList(null, null);
		}
		
		@Override
		protected void onPostExecute(List<Account> result) {
			mAccounts.addAll(result);
			mListAdapter.notifyDataSetChanged();
		}
		
	}
	
	private DBAccess mDba = null;
	
	private  List<Account> mAccounts = new ArrayList<Account>(); 
	private  AccountsArrayAdapter mListAdapter = null; 
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDba = new DBAccess(getActivity());
	}
	
	@Override
	public void onStart() {
		super.onStart();
		mListAdapter = new AccountsArrayAdapter(getActivity(), R.layout.accounts_list_item, mAccounts);
		setListAdapter(mListAdapter);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.accounts_fragment, container, false);
	}
}