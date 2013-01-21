package ru.interosite.openbooker.datamodel;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.interosite.openbooker.datamodel.domain.Account;
import ru.interosite.openbooker.datamodel.domain.BaseEntity;
import ru.interosite.openbooker.datamodel.domain.EntitiesFactory;
import ru.interosite.openbooker.datamodel.domain.ExpenseType;
import ru.interosite.openbooker.datamodel.domain.Funds;
import ru.interosite.openbooker.datamodel.domain.Operation;
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
					LOGGER.debug("Debit operation saved");
					return true;
				}
				else {
					LOGGER.debug("Failed update account");
					return false;
				}
			}
			else {
				LOGGER.debug("Failed insert debit operation");
				return false;
			}			
		}
		finally {
			dba.getWritableDatabase().endTransaction();
		}
	}
	
}