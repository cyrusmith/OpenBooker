package ru.interosite.openbooker.datamodel.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Account extends BaseEntity {

	private AccountType mType;
	private final Map<Currency, Funds> mFunds = new HashMap<Currency, Funds>();
	
	private final List<Operation> mOperations = new ArrayList<Operation>(); 
	
	Account(AccountType type, Funds initialBalance) {
		mType = type;
		mFunds.put(initialBalance.getCurrency(), initialBalance);
	}
	
	public void setType(AccountType type) {
		mType = type;
	}
	
	public AccountType getType() {
		return mType;
	}
		
	public Map<Currency, Funds> getBalance() {
		return mFunds;
	}
	
	public boolean hasFunds(Funds funds) {
		
		if(!mFunds.containsKey(funds.getCurrency())) {
			return false;
		}
		
		return mFunds.get(funds.getCurrency()).getValue() >= funds.getValue();
	}
	
	public void refill(Funds funds, IncomeSource source) {
		if(funds==null) {
			throw new IllegalArgumentException("Funds not set");
		}
		if(source==null) {
			throw new IllegalArgumentException("IncomeSource not set");
		}
		addFunds(funds);
	}	
		
	public void debit(Funds funds, ExpenseType type) {
		
		if(funds==null) {
			throw new IllegalArgumentException("Funds not set");
		}
		
		if(type==null) {
			throw new IllegalArgumentException("ExpenseType not set");
		}	
		
		addFunds(funds.reverse());
		this.setDirty();
		
		Operation debitOp = new OperationDebit(this, funds);
		debitOp.setDirty();
		mOperations.add(debitOp);
	}
	
	public void moveTo(Account account, Funds funds) throws UnsufficientFundsException {
		
		if(account==null) {
			throw new IllegalArgumentException("Account not set");
		}	
		
		if(funds==null) {
			throw new IllegalArgumentException("Funds not set");
		}
				
		if(!hasFunds(funds)) {
			throw new UnsufficientFundsException("No funds available");
		}
		
		addFunds(funds.reverse());
		account.addFunds(funds);
	}
	
	public List<Operation> getOperations() {
		return mOperations;
	}
	
	public Operation getLastOperation() {
		List<Operation> operations = getOperations(); 
		return operations.size() > 0 ? operations.get(operations.size() - 1)
				: null;
	}
	
	private void addFunds(Funds funds) {
		if(mFunds.containsKey(funds.getCurrency())) {
			Funds oldVal = mFunds.get(funds.getCurrency());
			funds = oldVal.plus(funds);
		}
		mFunds.put(funds.getCurrency(), funds);		
	}
	
}