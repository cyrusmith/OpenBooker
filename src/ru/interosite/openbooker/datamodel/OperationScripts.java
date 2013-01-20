package ru.interosite.openbooker.datamodel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.interosite.openbooker.datamodel.domain.Funds;

public class OperationScripts {

	private static Logger LOGGER = LoggerFactory.getLogger("ru.interosite.openbooker.datamodel.OperationScripts");  
	
	private static void assertDBA(DBAccess dba) {
		if(dba==null) {
			throw new IllegalArgumentException("DBAccess is null");
		}
	}
	
	private static void assertId(long val, String name) {
		if(val==0) {
			throw new IllegalArgumentException(name + " is 0");
		}
	}
	
	public static boolean debit(DBAccess dba, long accountId, Funds funds, long typeId) {

		assertDBA(dba);
		assertId(accountId, "accountId");
		assertId(typeId, "typeId");
		
		if(funds.getValue()==0) {	
			LOGGER.warn("Funds is empty. No operation made.");
			return true;
		}
		
		DomainRequestContext.create(dba);
		
		return true;
	}
	
}