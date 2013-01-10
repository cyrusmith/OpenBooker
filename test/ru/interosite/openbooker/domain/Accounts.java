package ru.interosite.openbooker.domain;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
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
import ru.interosite.openbooker.datamodel.domain.Operation;
import ru.interosite.openbooker.datamodel.domain.OperationRefill;
import ru.interosite.openbooker.datamodel.gateway.DatabaseGateway;
import ru.interosite.openbooker.datamodel.gateway.GatewayRegistry;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class Accounts {
	
	private long mAccId = -1;
	
	@Before
	public void setUp() {
		DBAccess dba = new DBAccess(Robolectric.application.getApplicationContext());
		Account acc = EntitiesFactory.createAccount(AccountType.CASH, new Funds(100000, Currency.RUR));
		mAccId = dba.getGatewayRegistry().get(Account.class).insert(acc);
		assertTrue(mAccId > 0);
		assertTrue(mAccId == 1);
		dba.close();
	}
	
	@Test
	public void findsAccounts() {				
		DBAccess dba = new DBAccess(Robolectric.application.getApplicationContext());
		GatewayRegistry gateways = dba.getGatewayRegistry();
		DatabaseGateway accountGateway = gateways.get(Account.class);
		Cursor c = accountGateway.findAll(null, null);
		assertThat(c, notNullValue());
	}
	
	@Test
	public void refillAccount() {
		
		//Params
		long accId = mAccId;
		Funds funds = new Funds(3306000, Currency.RUR);
		long incomeSourceId = 2; 
						
		DBAccess dba = new DBAccess(Robolectric.application.getApplicationContext());
		
		GatewayRegistry gateways = dba.getGatewayRegistry();			

		DatabaseGateway accountGateway = gateways.get(Account.class);
		DatabaseGateway operationGateway = gateways.get(OperationRefill.class);		

		Account account = (Account)accountGateway.findById(accId);
		IncomeSource source = (IncomeSource)gateways.get(IncomeSource.class).findById(incomeSourceId);
		
		Operation operation = EntitiesFactory.createRefillOperation(account, funds);
		
		account.addFunds(funds);
		
		SQLiteDatabase db = dba.getWritableDatabase();
		try {
			db.beginTransaction();
			int numUpdated = accountGateway.update(account);
			long newInserted = operationGateway.insert(operation);
			db.setTransactionSuccessful();
			assertTrue(numUpdated == 1);
			assertTrue(newInserted  > 0);			
		}
		catch(Exception e) {
			throw new AssertionError("Error during transaction");
		}
		finally {
			db.endTransaction();
		}
								
		dba.close();
	}
	
}