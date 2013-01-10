package ru.interosite.openbooker.domain;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.interosite.openbooker.datamodel.DBAccess;
import ru.interosite.openbooker.datamodel.domain.Account;
import ru.interosite.openbooker.datamodel.domain.AccountType;
import ru.interosite.openbooker.datamodel.domain.Currency;
import ru.interosite.openbooker.datamodel.domain.EntitiesFactory;
import ru.interosite.openbooker.datamodel.domain.Funds;
import ru.interosite.openbooker.datamodel.domain.IncomeSource;
import ru.interosite.openbooker.datamodel.gateway.DatabaseGateway;
import ru.interosite.openbooker.datamodel.gateway.GatewayRegistry;

import android.content.ContentValues;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class Accounts {
	
	@Test
	public void findsAccounts() {		
		
		DBAccess dba = new DBAccess(Robolectric.application.getApplicationContext());
		GatewayRegistry gateways = GatewayRegistry.createRegistry(dba);
		DatabaseGateway accountGateway = gateways.get(Account.class);
		Cursor c = accountGateway.findAll();
		
	}
	
	@Test
	public void refillAccount() {
		
		//Params
		long accId = 1;
		Funds funds = new Funds(3306000, Currency.RUR);
		long incomeSourceId = 2; 
						
		DBAccess dba = new DBAccess(Robolectric.application.getApplicationContext());
		
		GatewayRegistry gateways = dba.getGatewayRegistry();			

		DatabaseGateway accountGateway = gateways.get(Account.class);
		DatabaseGateway operationGateway = gateways.get(OperationRefill.class);		

		Account account = (Account)accountGateway.findById(accId);
		IncomeSource source = (IncomeSource)gateways.get(IncomeSource.class).findById(incomeSourceId);
		
		Operation operation = EntitiesFactory.createRefillOperation(account, source, funds);
		
		account.addFunds(funds);
		
		SQLiteDatabase db = dba.getWritableDatabase();
		try {
			db.beginTransaction();
			int numUpdated = accountGateway.update(account);
			int newInserted = operationGateway.insert(operation);
			db.setTransactionSuccessful();
		}
		finally {
			db.endTransaction();
		}
								
		assertTrue(numUpdated == 1);
		assertTrue(newInserted  > 0);
		
		dba.close();
	}
	
}