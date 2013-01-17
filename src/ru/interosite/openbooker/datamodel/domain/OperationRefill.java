package ru.interosite.openbooker.datamodel.domain;


import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class OperationRefill extends Operation {
	
	public static final String ACCOUNT_ID = "account_id";
	public static final String CURRENCY_CODE = "currency_code";
	public static final String VALUE = "value";
	
	private Account mAccount = null;
	private Funds mFunds = null;
	private IncomeSource mSource = null;
	
	OperationRefill() {}
	
	public void setAccount(Account account) {
		mAccount = account;
	}
	
	public void setFunds(Funds funds) {
		mFunds = funds;
	}
	
	public Account getAccount() {
		return mAccount;
	}
	
	public Funds getFunds() {
		return mFunds;
	}

	public void setIncomeSource(IncomeSource source) {
		mSource = source;
	}
	
	public IncomeSource getIncomeSource() {
		return mSource;
	}	
	
	@Override
	public OperationType getType() {
		return OperationType.REFILL;
	}
	
	@Override
	public void configFromJson(String json) throws JSONException {
		JSONObject data = (JSONObject)new JSONTokener(json).nextValue();
	}
	
	@Override
	public String getDataJson() {
		String jsonString = "{}";
		JSONObject jsonObj = new JSONObject();		
		try {
			long accId = mAccount.getId()==null? 0 : mAccount.getId();
			jsonObj.put(ACCOUNT_ID, String.valueOf(accId));
			if(mFunds!=null) {
				jsonObj.put(CURRENCY_CODE, mFunds.getCurrency().getCurrencyCode());
				jsonObj.put(VALUE, mFunds.getValue());
			}
			jsonString = jsonObj.toString();
		} catch (JSONException e) {
		}
		return jsonString;
	}
	
}