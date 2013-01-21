package ru.interosite.openbooker.datamodel.domain;

import org.json.JSONException;
import org.json.JSONObject;


public class OperationDebit extends Operation {	
	
	private static final String TAG = "ru.interosite.openbooker.datamodel.domain.OperationDebit";
	
	public static final String ACCOUNT_ID = "account_id";
	public static final String EXPENSE_TYPE_ID = "expense_type_id";
	
	private Account mAccount = null;
	private ExpenseType mExpenseType = null;
	
	OperationDebit() {}		
	
	@Override
	public OperationType getType() {
		return OperationType.DEBIT;
	}
	
	public void setAccount(Account account) {
		mAccount = account;
	}
	
	public Account getAccount() {
		return mAccount;
	}
	
	public void setExpenseType(ExpenseType type) {
		mExpenseType = type;
	}
	
	public ExpenseType getExpenseType() {
		return mExpenseType;
	}
	
	@Override
	public JSONObject getDataJson() {
		JSONObject jsonObj = super.getDataJson();		
		try {
			long accId = mAccount.getId()==null? 0 : mAccount.getId();
			long extTypeId = mExpenseType.getId()==null? 0 : mExpenseType.getId();
			jsonObj.put(ACCOUNT_ID, String.valueOf(accId));
			jsonObj.put(EXPENSE_TYPE_ID, String.valueOf(extTypeId));
		} catch (JSONException e) {
			jsonObj = new JSONObject();
		}
		return jsonObj;
	}
	
}