package ru.interosite.openbooker.datamodel.domain;

public class OperationDebit extends Operation {
	
	private Funds mFunds = null;
	
	public OperationDebit(Account parentAccount, Funds funds) {
		super(parentAccount);
		mFunds = funds;
	}
	
	public void setFunds(Funds funds) {
		mFunds = funds;
	}
	
	public Funds getFunds() {
		return mFunds;
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		super.execute();
	}
	
}