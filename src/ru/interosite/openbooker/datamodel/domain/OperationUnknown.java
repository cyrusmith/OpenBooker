package ru.interosite.openbooker.datamodel.domain;


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
	public OperationType getType() {
		return OperationType.UNKNOWN;
	}	
}