package ru.interosite.openbooker.datamodel.domain;


public class OperationDebit extends Operation {	
	OperationDebit() {}		
	
	@Override
	public OperationType getType() {
		return OperationType.DEBIT;
	}
	
}