package ru.interosite.openbooker.datamodel;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.interosite.openbooker.datamodel.domain.Account;
import ru.interosite.openbooker.datamodel.domain.BaseEntity;
import ru.interosite.openbooker.datamodel.domain.EntitiesFactory;
import ru.interosite.openbooker.datamodel.domain.ExpenseType;
import ru.interosite.openbooker.datamodel.domain.Funds;
import ru.interosite.openbooker.datamodel.domain.IncomeSource;
import ru.interosite.openbooker.datamodel.domain.Operation;
import ru.interosite.openbooker.datamodel.domain.OperationMove;
import ru.interosite.openbooker.datamodel.domain.OperationRefill;
import ru.interosite.openbooker.datamodel.domain.Operation.OperationType;
import ru.interosite.openbooker.datamodel.domain.OperationDebit;
import ru.interosite.openbooker.datamodel.gateway.GatewayRegistry;

public class OperationScripts {

	private static String TAG = "ru.interosite.openbooker.datamodel.OperationScripts";  
	
	private static void assertDRQ(DomainRequestContext request) {
		if(request==null) {
			throw new IllegalArgumentException("DomainRequestContext is null");
		}
	}
	
	private static void assertId(long val, String name) {
		if(val==0) {
			throw new IllegalArgumentException(name + " is 0");
		}
	}
	
	public static boolean debit(DomainRequestContext request, long accountId, Funds funds, long typeId) {

		assertDRQ(request);
		assertId(accountId, "accountId");
		assertId(typeId, "typeId");
		
		if(funds.getValue()==0) {	
			LoggerFactory.getLogger(TAG).warn("Funds is empty. No operation made.");
			return true;
		}
		
		DBAccess dba = request.getDba();
		GatewayRegistry gateways = request.getGatewayRegistry();
		EntitiesFactory factory = request.getEntitiesFactory();
		
		BaseEntity accEntity = gateways.get(Account.class).findById(accountId);		
		if(!(accEntity instanceof Account)) {
			LoggerFactory.getLogger(TAG).warn("Cannot find Account with id={}", accountId);
			return false;
		}
		
		BaseEntity expTypeEntity = gateways.get(ExpenseType.class).findById(typeId);		
		if(!(expTypeEntity instanceof ExpenseType)) {
			LoggerFactory.getLogger(TAG).warn("Cannot find ExpenseType with id={}", typeId);
			return false;
		}		
		
		Account account = (Account)accEntity;
		ExpenseType expenseType = (ExpenseType)expTypeEntity;
		
		OperationDebit operDebit = (OperationDebit)factory.createOperation(OperationType.DEBIT);
		operDebit.setDateTime(Calendar.getInstance().getTimeInMillis());
		operDebit.setFunds(funds);
		
		operDebit.setAccount(account);
		operDebit.setExpenseType(expenseType);
							
		account.addFunds(funds.reverse());
		
		//TODO update analytics
		
		try {
			dba.getWritableDatabase().beginTransaction();
			long newOperId = gateways.get(Operation.class).insert(operDebit);
			if(newOperId > 0) {
				int rowUpdated = request.getGatewayRegistry().get(Account.class).update(account);
				if(rowUpdated==1) {
					dba.getWritableDatabase().setTransactionSuccessful();
					LoggerFactory.getLogger(TAG).warn("Debit operation saved");
					return true;
				}
				else {
					LoggerFactory.getLogger(TAG).warn("Failed update account");
					return false;
				}
			}
			else {
				LoggerFactory.getLogger(TAG).warn("Failed insert debit operation");
				return false;
			}			
		}
		finally {
			dba.getWritableDatabase().endTransaction();
		}
	}
	
	public static boolean refill(DomainRequestContext request, long accountId, Funds funds, long sourceId) {
		
		assertDRQ(request);
		assertId(accountId, "accountId");
		assertId(sourceId, "sourceId");
		
		if(funds.getValue()==0) {	
			LoggerFactory.getLogger(TAG).warn("Funds is empty. No operation made.");
			return true;
		}
		
		DBAccess dba = request.getDba();
		GatewayRegistry gateways = request.getGatewayRegistry();
		EntitiesFactory factory = request.getEntitiesFactory();
		
		BaseEntity accEntity = gateways.get(Account.class).findById(accountId);		
		if(!(accEntity instanceof Account)) {
			LoggerFactory.getLogger(TAG).warn("Cannot find Account with id={}", accountId);
			return false;
		}
		
		BaseEntity sourceEntity = gateways.get(IncomeSource.class).findById(sourceId);		
		if(!(sourceEntity instanceof IncomeSource)) {
			LoggerFactory.getLogger(TAG).warn("Cannot find IncomeSource with id={}", sourceId);
			return false;
		}		
		
		Account account = (Account)accEntity;
		IncomeSource source = (IncomeSource)sourceEntity;
		
		OperationRefill operRefill = (OperationRefill)factory.createOperation(OperationType.REFILL);
		operRefill.setDateTime(Calendar.getInstance().getTimeInMillis());
		operRefill.setFunds(funds);
		
		operRefill.setAccount(account);
		operRefill.setIncomeSource(source);
							
		account.addFunds(funds);		
		try {
			dba.getWritableDatabase().beginTransaction();
			long newOperId = gateways.get(Operation.class).insert(operRefill);
			if(newOperId > 0) {
				int rowUpdated = request.getGatewayRegistry().get(Account.class).update(account);
				if(rowUpdated==1) {
					dba.getWritableDatabase().setTransactionSuccessful();
					LoggerFactory.getLogger(TAG).warn("Refill operation saved");
					return true;
				}
				else {
					LoggerFactory.getLogger(TAG).warn("Failed update account");
					return false;
				}
			}
			else {
				LoggerFactory.getLogger(TAG).warn("Failed insert refill operation");
				return false;
			}			
		}
		finally {
			dba.getWritableDatabase().endTransaction();
		}		
	}	
	
	public static boolean move(DomainRequestContext request, long accFromId, long accToId, Funds funds) {
		
		assertDRQ(request);
		assertId(accFromId, "accFromId");
		assertId(accToId, "accToId");
		
		if(funds.getValue()==0) {	
			LoggerFactory.getLogger(TAG).warn("Funds is empty. No operation made.");
			return true;
		}		
	
		DBAccess dba = request.getDba();
		GatewayRegistry gateways = request.getGatewayRegistry();
		EntitiesFactory factory = request.getEntitiesFactory();		
		
		BaseEntity accFromEntity = gateways.get(Account.class).findById(accFromId);		
		if(!(accFromEntity instanceof Account)) {
			LoggerFactory.getLogger(TAG).warn("Cannot find Account with id={}", accFromId);
			return false;
		}
		
		BaseEntity accToEntity = gateways.get(Account.class).findById(accToId);		
		if(!(accToEntity instanceof Account)) {
			LoggerFactory.getLogger(TAG).warn("Cannot find Account with id={}", accToId);
			return false;
		}
				
		Account accFrom = (Account)accFromEntity;
		Account accTo = (Account)accToEntity;
		
		if(!accFrom.hasSufficientFunds(funds)) {
			return false;
		}
		
		OperationMove opMove = (OperationMove)factory.createOperation(OperationType.MOVE);
		
		opMove.setDateTime(Calendar.getInstance().getTimeInMillis());
		opMove.setFunds(funds);
		
		opMove.setAccountFrom(accFrom);
		opMove.setAccountTo(accTo);

		accFrom.addFunds(funds.reverse());
		accTo.addFunds(funds);
		
		dba.getWritableDatabase().beginTransaction();		
		try {
			long newOpId = gateways.get(Operation.class).insert(opMove);
			if(newOpId<1) {
				LoggerFactory.getLogger(TAG).warn("Failed inserting move operation");
				return false;
			}
			int updNum = gateways.get(Account.class).update(accFrom);
			if(updNum!=1) {
				LoggerFactory.getLogger(TAG).warn("Failed updating account {}", accFrom.getId());
				return false;
			}			
			updNum = gateways.get(Account.class).update(accTo);
			if(updNum!=1) {
				LoggerFactory.getLogger(TAG).warn("Failed updating account {}", accTo.getId());
				return false;
			}			
			dba.getWritableDatabase().setTransactionSuccessful();
			return true;
		}
		catch(Exception e) {
			LoggerFactory.getLogger(TAG).warn("Failed database funds move operation");
			return false;
		}
		finally {
			dba.getWritableDatabase().endTransaction();
		}
	}	
}