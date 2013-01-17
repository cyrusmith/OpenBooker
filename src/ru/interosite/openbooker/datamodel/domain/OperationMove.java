package ru.interosite.openbooker.datamodel.domain;


public class OperationMove extends Operation {	
	OperationMove() {}		
	
	@Override
	public OperationType getType() {
		return OperationType.MOVE;
	}
	
}