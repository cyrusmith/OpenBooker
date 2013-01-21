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

import ru.interosite.openbooker.ApplicationConfig;
import ru.interosite.openbooker.datamodel.DBAccess;
import ru.interosite.openbooker.datamodel.DomainRequestContext;
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
	
	private GatewayRegistry mGatewayRegistry = null;
	private EntitiesFactory mEntitiesFactory = null;
	
	@Before
	public void setUp() {
		System.setProperty("robolectric.logging", "stdout");
		ApplicationConfig.getInstance().init(Robolectric.application.getApplicationContext());
		DomainRequestContext.create(new DBAccess(Robolectric.application.getApplicationContext()));
		mEntitiesFactory = DomainRequestContext.getInstance().getEntitiesFactory();
		mGatewayRegistry = DomainRequestContext.getInstance().getGatewayRegistry();
		createSomeData();
	}
	private void createSomeData() {
		Account acc = mEntitiesFactory.createAccount(AccountType.CASH, "Wallet", new Funds(100000, Currency.getInstance("RUR")));
		mAccId = mGatewayRegistry.get(Account.class).insert(acc);
		assertEquals(1, mAccId);
		
		assertThat(mGatewayRegistry, instanceOf(GatewayRegistry.class));
		
		IncomeSource source = mEntitiesFactory.createIncomeSource("Salary");
		
		mIncomeId = mGatewayRegistry.get(IncomeSource.class).insert(source);
		assertEquals(1, mIncomeId);
		
	}
	
	@Test
	public void findsAccounts() {				

		DatabaseGateway accountGateway = mGatewayRegistry.get(Account.class);
		Cursor c = accountGateway.findAll(null, null);
		assertThat(c, notNullValue());
	}
	
	@Test
	public void refillAccount() {
		
		//Params
		long accId = mAccId;
		Funds funds = new Funds(3306000, Currency.getInstance("RUR"));
		long incomeSourceId = mIncomeId; 
						
		DatabaseGateway accountGateway = mGatewayRegistry.get(Account.class);
		DatabaseGateway operationGateway = mGatewayRegistry.get(Operation.class);		

		BaseEntity accEntity = accountGateway.findById(accId);
		assertThat(accEntity, notNullValue());
		assertThat(accEntity, instanceOf(Account.class));
		assertTrue(!BaseEntity.isUnknown(accEntity));
		
		Account account = (Account)accEntity;
		
		IncomeSource source = (IncomeSource)mGatewayRegistry.get(IncomeSource.class).findById(incomeSourceId);
		assertThat(source, notNullValue());
		
		OperationRefill operation = (OperationRefill)mEntitiesFactory.createOperation(OperationType.REFILL);
		
		operation.setAccount(account);
		operation.setFunds(funds);
		
		account.addFunds(funds);
		
		SQLiteDatabase db = DomainRequestContext.getInstance().getDba().getWritableDatabase();
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