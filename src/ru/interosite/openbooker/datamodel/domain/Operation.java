package ru.interosite.openbooker.datamodel.domain;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.slf4j.LoggerFactory;

public abstract class Operation extends BaseEntity {
	
	private static final String TAG = ""; 
	
	public static enum OperationType {
		
		UNKNOWN(""),
		REFILL("refill"),
		DEBIT("debit"),
		MOVE("move");
		
		private static final Map<String, OperationType> mMap = new HashMap<String, OperationType>(); 
		
		static {
			mMap.put("", UNKNOWN);	
			mMap.put("refill", REFILL);	
			mMap.put("debit", DEBIT);	
			mMap.put("move", MOVE);	
		}
		
		private final String mName;
		
		private OperationType(String name) {
			mName = name;
		}
		
		@Override
		public String toString() {
			return mName;
		}
		
		public OperationType forName(String from) {
			if(mMap.containsKey(from)) {
				return mMap.get(from);
			}
			return UNKNOWN;
		}
						
	}
			
	private static final Map<OperationType, Class<? extends Operation>> mSubclassesMap = new HashMap<Operation.OperationType, Class<? extends Operation>>();
	
	static {
		mSubclassesMap.put(OperationType.REFILL, OperationRefill.class);
		mSubclassesMap.put(OperationType.DEBIT, OperationDebit.class);
		mSubclassesMap.put(OperationType.MOVE, OperationMove.class);
	}
	
	public static final Operation newInstance(OperationType type) {
		Operation instance = null;
		if(mSubclassesMap.containsKey(type)) {
			Class<? extends Operation> operationClass = mSubclassesMap.get(type);
			try {
				Constructor<? extends Operation> constr = operationClass.getDeclaredConstructor();
				instance = constr.newInstance();
			} catch (Exception e) {
				LoggerFactory.getLogger(TAG).error("Exception when try to instantiate operation of type {}. Error message: {}", type.toString(), e.getMessage());
			}
		}		
		return instance!=null? instance : OperationUnknown.getInstance();
	}
	
	private long mDateTime = 0;  
	
	Operation() {}
	
	public void setDateTime(long dateTime) {
		mDateTime = dateTime;
	}
	
	public long getDateTime() {
		return mDateTime;
	}
	
	public void configFromJson(String json) throws JSONException {
	}
	
	public String getDataJson() {
		return "{}";
	}
	
	public abstract OperationType getType();
	
}