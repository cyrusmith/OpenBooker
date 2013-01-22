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
import ru.interosite.openbooker.datamodel.domain.OperationRefill;
import ru.interosite.openbooker.datamodel.domain.Operation.OperationType;
import ru.interosite.openbooker.datamodel.domain.OperationDebit;
import ru.interosite.openbooker.datamodel.gateway.GatewayRegistry;

public class OperationScripts {

	private static Logger LOGGER = LoggerFactory.getLogger("ru.interosite.openbooker.datamodel.OperationScripts");  
	
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
			LOGGER.warn("Funds is empty. No operation made.");
			return true;
		}
		
		DBAccess dba = request.getDba();
		GatewayRegistry gateways = request.getGatewayRegistry();
		EntitiesFactory factory = request.getEntitiesFactory();
		
		BaseEntity accEntity = gateways.get(Account.class).findById(accountId);		
		if(!(accEntity instanceof Account)) {
			LOGGER.warn("Cannot find Account with id={}", accountId);
			return false;
		}
		
		BaseEntity expTypeEntity = gateways.get(ExpenseType.class).findById(typeId);		
		if(!(expTypeEntity instanceof ExpenseType)) {
			LOGGER.warn("Cannot find ExpenseType with id={}", typeId);
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
					LOGGER.warn("Debit operation saved");
					return true;
				}
				else {
					LOGGER.warn("Failed update account");
					return false;
				}
			}
			else {
				LOGGER.warn("Failed insert debit operation");
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
			LOGGER.warn("Funds is empty. No operation made.");
			return true;
		}
		
		DBAccess dba = request.getDba();
		GatewayRegistry gateways = request.getGatewayRegistry();
		EntitiesFactory factory = request.getEntitiesFactory();
		
		BaseEntity accEntity = gateways.get(Account.class).findById(accountId);		
		if(!(accEntity instanceof Account)) {
			LOGGER.warn("Cannot find Account with id={}", accountId);
			return false;
		}
		
		BaseEntity sourceEntity = gateways.get(IncomeSource.class).findById(sourceId);		
		if(!(sourceEntity instanceof IncomeSource)) {
			LOGGER.warn("Cannot find IncomeSource with id={}", sourceId);
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
					LOGGER.warn("Refill operation saved");
					return true;
				}
				else {
					LOGGER.warn("Failed update account");
					return false;
				}
			}
			else {
				LOGGER.warn("Failed insert refill operation");
				return false;
			}			
		}
		finally {
			dba.getWritableDatabase().endTransaction();
		}
		
	}
	
}