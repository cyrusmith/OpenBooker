package ru.interosite.openbooker;

import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
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
import ru.interosite.openbooker.datamodel.tables.AccountTypeTableModel;
import ru.interosite.openbooker.datamodel.tables.AccountsTableModel;
import ru.interosite.openbooker.datamodel.tables.TableModel;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.view.ViewGroup;

public class AccountsFragment extends ListFragment {
	
	private class ListViewBinder implements SimpleCursorAdapter.ViewBinder {
		
		private Map<String, Integer> mColIdx = new HashMap<String, Integer>();
		
		ListViewBinder() {
			
			String[] cols = TableModel.getModel(AccountsTableModel.class).getColumnNames();
			
			for(int i=0; i< cols.length; i++) {
				mColIdx.put(cols[i], i);
			}
			
		}
		
		@Override
		public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
			return true;
		}		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDba = new DBAccess(getActivity());
	}
		
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		setListAdapter(mAdapter);		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.accounts_fragment, container, false);
	}
	
	
	private final String[] PROJECTION = {
		AccountsTableModel.ID,	
		AccountsTableModel.TITLE,	
		AccountsTableModel.TYPE_ID	
	};
		
	private final int[] VIEWS = {1, 2, 3};
	
	private DBAccess mDba = null;	
	private  List<Account> mAccounts = new ArrayList<Account>(); 
	private  SimpleCursorAdapter mAdapter = null; 
	
}