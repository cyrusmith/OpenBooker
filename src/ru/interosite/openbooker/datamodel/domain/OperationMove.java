package ru.interosite.openbooker.datamodel.domain;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;


public class OperationMove extends Operation {	
	
	private static final String TAG = "ru.interosite.openbooker.datamodel.domain.OperationMove";
	
	public static final String ACCOUNT_FROM_ID = "account_from_id";
	public static final String ACCOUNT_TO_ID = "account_to_id";
	
	private Account mAccountFrom = null;
	private Account mAccountTo = null;
	
	OperationMove() {}		
	
	public void setAccountFrom(Account acc) {
		if(acc==null) {
			throw new IllegalArgumentException("Account is null");
		}
		if(acc.getId()==null) {
			throw new IllegalArgumentException("Account does not have an id");
		}
		mAccountFrom = acc;
	}
	
	public void setAccountTo(Account acc) {		
		if(acc==null) {
			throw new IllegalArgumentException("Account is null");
		}		
		if(acc.getId()==null) {
			throw new IllegalArgumentException("Account does not have an id");
		}		
		mAccountTo = acc;
	}
	
	public Account getAccountFrom() {
		return mAccountFrom;
	}
	
	public Account getAccountTo() {
		return mAccountTo;
	}
	
	@Override
	public void configFromJson(String json) throws JSONException {
		JSONObject jsonObj = new JSONObject(json);
		long accFromId = jsonObj.getLong(ACCOUNT_FROM_ID);
		long accToId = jsonObj.getLong(ACCOUNT_TO_ID);

		throw new UnsupportedOperationException();
	}
	
	@Override
	public String getDataJson() {
		JSONObject jsonObj = new JSONObject();		
		try {
			jsonObj.put(ACCOUNT_FROM_ID, String.valueOf(mAccountFrom.getId()));
			jsonObj.put(ACCOUNT_TO_ID, String.valueOf(mAccountTo.getId()));
		} catch (JSONException e) {
			LoggerFactory.getLogger(TAG).warn("Error during json operation: {}", e);
			jsonObj = new JSONObject(); 
		}
		return jsonObj.toString();
	}	
	
	@Override
	public OperationType getType() {
		return OperationType.MOVE;
	}
	
}