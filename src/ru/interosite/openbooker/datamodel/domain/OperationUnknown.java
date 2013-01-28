package ru.interosite.openbooker.datamodel.domain;

import org.json.JSONException;


public class OperationUnknown extends Operation {
	private static final ThreadLocal<OperationUnknown> mInstance = new ThreadLocal<OperationUnknown>() {
		protected OperationUnknown initialValue() {
			return new OperationUnknown();
		};
	};
	private OperationUnknown() {}
	public static OperationUnknown getInstance() {
		return mInstance.get();
	}
	
	@Override
	public void configFromJson(String json) throws JSONException {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public OperationType getType() {
		return OperationType.UNKNOWN;
	}	
}