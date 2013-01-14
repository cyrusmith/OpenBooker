package ru.interosite.openbooker.domain;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.Currency;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.interosite.openbooker.datamodel.DBAccess;
import ru.interosite.openbooker.datamodel.domain.Account;
import ru.interosite.openbooker.datamodel.domain.AccountType;
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
	private DBAccess mDba = null;
	
	@Before
	public void setUp() {
		mDba = new DBAccess(Robolectric.application.getApplicationContext());
		Account acc = EntitiesFactory.createAccount(AccountType.CASH, new Funds(100000, Currency.getInstance("RUR")));
		acc.setTitle("Wallet");
		mAccId = mDba.getGatewayRegistry().get(Account.class).insert(acc);
		assertEquals(1, mAccId);
	}
	
	@After
	public void tearDown() {
		mDba.close();
	}
	
	@Test
	public void findsAccounts() {				
		GatewayRegistry gateways = mDba.getGatewayRegistry();
		DatabaseGateway accountGateway = gateways.get(Account.class);
		Cursor c = accountGateway.findAll(null, null);
		assertThat(c, notNullValue());
	}
	
	@Test
	public void refillAccount() {
		
		//Params
		long accId = mAccId;
		Funds funds = new Funds(3306000, Currency.getInstance("RUR"));
		long incomeSourceId = 2; 
						
		GatewayRegistry gateways = mDba.getGatewayRegistry();			

		DatabaseGateway accountGateway = gateways.get(Account.class);
		DatabaseGateway operationGateway = gateways.get(OperationRefill.class);		

		Account account = (Account)accountGateway.findById(accId);
		assertThat(account, notNullValue());
		
		IncomeSource source = (IncomeSource)gateways.get(IncomeSource.class).findById(incomeSourceId);
		assertThat(source, notNullValue());
		
		Operation operation = EntitiesFactory.createRefillOperation(account, funds);
		
		account.addFunds(funds);
		
		SQLiteDatabase db = mDba.getWritableDatabase();
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
								
	}
	
}