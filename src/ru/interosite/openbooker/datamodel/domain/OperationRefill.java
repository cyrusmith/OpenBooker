package ru.interosite.openbooker.datamodel.domain;


import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import ru.interosite.openbooker.datamodel.DomainRequestContext;
import ru.interosite.openbooker.datamodel.gateway.GatewayRegistry;
import ru.interosite.openbooker.datamodel.gateway.IncomeSourceGateway;

public class OperationRefill extends Operation {

	private static final String TAG = "ru.interosite.openbooker.datamodel.domain.OperationRefill";
	
	public static final String ACCOUNT_ID = "account_id";
	public static final String SOURCE_ID = "income_source_id";
	
	private Account mAccount = null;
	private IncomeSource mSource = null;
	
	OperationRefill() {}
	
	public void setAccount(Account account) {
		mAccount = account;
	}
	
	public Account getAccount() {
		return mAccount;
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
		
		long accId = data.getLong(ACCOUNT_ID);
		long sourceId = data.getLong(SOURCE_ID);
		
		if(accId > 0) {
			BaseEntity accEntity = DomainRequestContext.getInstance().getGatewayRegistry().get(Account.class).findById(accId);
			if(!(accEntity instanceof Account)) {
				throw new IllegalStateException("Cannot find account with id " + accId);
			}
			
			Account account = (Account)accEntity;
			setAccount(account);
			
		}
		else {
			throw new IllegalStateException("Account id not set for refill operation");
		}
		
		if(sourceId > 0) {
			BaseEntity sourceEntity = DomainRequestContext.getInstance().getGatewayRegistry().get(IncomeSource.class).findById(sourceId);
			if(!(sourceEntity instanceof IncomeSource)) {
				throw new IllegalStateException("Cannot find IncomeSource with id " + sourceId);
			}
			
			IncomeSource source = (IncomeSource)sourceEntity;
			setIncomeSource(source);
			
		}
		else {
			throw new IllegalStateException("IncomeSource id not set for refill operation");
		}		
	}
	
	@Override
	public JSONObject getDataJson() {
		JSONObject jsonObj = super.getDataJson();		
		try {
			long accId = mAccount.getId()==null? 0 : mAccount.getId();
			jsonObj.put(ACCOUNT_ID, String.valueOf(accId));
		} catch (JSONException e) {
			jsonObj = new JSONObject();
		}
		return jsonObj;
	}
	
}