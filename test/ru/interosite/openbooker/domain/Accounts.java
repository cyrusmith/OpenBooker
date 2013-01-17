package ru.interosite.openbooker.domain;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.instanceOf;
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
import ru.interosite.openbooker.datamodel.domain.BaseEntity;
import ru.interosite.openbooker.datamodel.domain.EntitiesFactory;
import ru.interosite.openbooker.datamodel.domain.Funds;
import ru.interosite.openbooker.datamodel.domain.IncomeSource;
import ru.interosite.openbooker.datamodel.domain.Operation;
import ru.interosite.openbooker.datamodel.domain.OperationRefill;
import ru.interosite.openbooker.datamodel.domain.Operation.OperationType;
import ru.interosite.openbooker.datamodel.gateway.DatabaseGateway;
import ru.interosite.openbooker.datamodel.gateway.GatewayRegistry;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class Accounts {
	
	private long mAccId = -1;
	private long mIncomeId = -1;
	private DBAccess mDba = null;
	
	private void createSomeData() {
		Account acc = EntitiesFactory.createAccount(AccountType.CASH, new Funds(100000, Currency.getInstance("RUR")));
		acc.setTitle("Wallet");
		mAccId = mDba.getGatewayRegistry().get(Account.class).insert(acc);
		assertEquals(1, mAccId);
		
		IncomeSource source = EntitiesFactory.createIncomeSource("Salary");
		
		mIncomeId = mDba.getGatewayRegistry().get(IncomeSource.class).insert(source);
		assertEquals(1, mIncomeId);
		
	}
	
	@Before
	public void setUp() {
		System.setProperty("robolectric.logging", "stdout");	
		mDba = new DBAccess(Robolectric.application.getApplicationContext());
		createSomeData();
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
		long incomeSourceId = mIncomeId; 
						
		GatewayRegistry gateways = mDba.getGatewayRegistry();			

		DatabaseGateway accountGateway = gateways.get(Account.class);
		DatabaseGateway operationGateway = gateways.get(Operation.class);		

		BaseEntity accEntity = accountGateway.findById(accId);
		assertThat(accEntity, notNullValue());
		assertThat(accEntity, instanceOf(Account.class));
		assertTrue(!BaseEntity.isUnknown(accEntity));
		
		Account account = (Account)accEntity;
		
		IncomeSource source = (IncomeSource)gateways.get(IncomeSource.class).findById(incomeSourceId);
		assertThat(source, notNullValue());
		
		OperationRefill operation = (OperationRefill)EntitiesFactory.createOperation(OperationType.REFILL);
		
		operation.setAccount(account);
		operation.setFunds(funds);
		
		account.addFunds(funds);
		
		SQLiteDatabase db = mDba.getWritableDatabase();
		try {
			assertThat(operation.getId(), nullValue());
			db.beginTransaction();
			int numUpdated = accountGateway.update(account);
			long newInserted = operationGateway.insert(operation);
			db.setTransactionSuccessful();
			assertTrue(numUpdated == 1);
			assertTrue(newInserted  == 1);				
			assertThat(operation.getId(), notNullValue());
		}
		finally {
			db.endTransaction();
		}							
	}	
}