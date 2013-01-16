package ru.interosite.openbooker.datamodel.domain;


public class Operation extends BaseEntity {
	
	public static enum OperationType {
		
		REFILL("refill"),
		DEBIT("debit");
		
		private final String mName;
		
		private OperationType(String name) {
			mName = name;
		}
		
		@Override
		public String toString() {
			return mName;
		}
		
		valueO
	}
	
	protected final Account mAccount;
	
	Operation(Account parentAccount) {
		if(parentAccount==null) {
			throw new IllegalArgumentException("Parent account not set for Operation");
		}
		mAccount = parentAccount;
	}
	
	public void execute() {
		//TODO make dirty
	}

}