package ru.interosite.openbooker.domain;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.Currency;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.interosite.openbooker.ApplicationConfig;
import ru.interosite.openbooker.datamodel.DBAccess;
import ru.interosite.openbooker.datamodel.DomainRequestContext;
import ru.interosite.openbooker.datamodel.OperationScripts;
import ru.interosite.openbooker.datamodel.domain.Account;
import ru.interosite.openbooker.datamodel.domain.AccountType;
import ru.interosite.openbooker.datamodel.domain.BaseEntity;
import ru.interosite.openbooker.datamodel.domain.EntitiesFactory;
import ru.interosite.openbooker.datamodel.domain.ExpenseType;
import ru.interosite.openbooker.datamodel.domain.Funds;
import ru.interosite.openbooker.datamodel.domain.IncomeSource;
import ru.interosite.openbooker.datamodel.domain.Operation;
import ru.interosite.openbooker.datamodel.domain.OperationRefill;
import ru.interosite.openbooker.datamodel.domain.Operation.OperationType;
import ru.interosite.openbooker.datamodel.gateway.AccountGateway;
import ru.interosite.openbooker.datamodel.gateway.DatabaseGateway;
import ru.interosite.openbooker.datamodel.gateway.ExpenseTypeGateway;
import ru.interosite.openbooker.datamodel.gateway.GatewayRegistry;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class OperationScriptsTest {
	
	private DBAccess mDba = null;

	private void createSomeData() {
		
		DomainRequestContext.create(mDba);
		GatewayRegistry gatewaysRegistry = DomainRequestContext.getInstance().getGatewayRegistry();
		EntitiesFactory entitiesFactory = DomainRequestContext.getInstance().getEntitiesFactory();
		
		Account acc = entitiesFactory.createAccount(AccountType.CASH, "Wallet", Funds.EMPTY);
		AccountGateway accGateway = (AccountGateway)gatewaysRegistry.get(Account.class);
		
		long newAccId = accGateway.insert(acc);
		assertEquals(1, newAccId);
		
		Account acc2 = entitiesFactory.createAccount(AccountType.DEBIT_CARD, "Salary card", Funds.EMPTY);
		
		long newAccId2 = accGateway.insert(acc2);
		assertEquals(2, newAccId2);
		
		ExpenseType extType = entitiesFactory.createExpenseType("���", ExpenseType.ROOT);
		ExpenseTypeGateway expGateway = (ExpenseTypeGateway)gatewaysRegistry.get(ExpenseType.class);
		long newExtTypeId = expGateway.insert(extType);
		
		assertEquals(1, newExtTypeId);
		
		IncomeSource sourceType = entitiesFactory.createIncomeSource("���");
		long sourceTypeId = gatewaysRegistry.get(IncomeSource.class).insert(sourceType);
		
		assertEquals(1, sourceTypeId);
		
	}
	
	@Before
	public void setUp() {
		System.setProperty("robolectric.logging", "stdout");
		ApplicationConfig.getInstance().init(Robolectric.application.getApplicationContext());
		mDba = new DBAccess(Robolectric.application.getApplicationContext());	
		createSomeData();
	}
	
	@Test
	public void debitScript() {
		
		long accId = 1;
		Funds fundsRUR = new Funds(100000, Currency.getInstance("RUR"));
		Funds fundsUSD = new Funds(66600, Currency.getInstance("USD"));
		long expenceTypeId = 1; 
		
		DomainRequestContext domainContext = DomainRequestContext.create(mDba);
		
		boolean res = OperationScripts.debit(domainContext, accId, fundsRUR, expenceTypeId);		
		assertTrue(res);
		
		res = OperationScripts.debit(domainContext, accId, fundsUSD, expenceTypeId);		
		assertTrue(res);
				
		////////////////////////////
		DomainRequestContext.create(mDba);
		GatewayRegistry gatewaysRegistry = DomainRequestContext.getInstance().getGatewayRegistry();
		
		AccountGateway account = (AccountGateway)gatewaysRegistry.get(Account.class);
		
		Account acc = (Account)account.findById(accId);
		
		Map<Currency, Funds> accFunds = acc.getFunds();
		
		assertEquals(2, accFunds.size());
		
		assertThat(accFunds.get(Currency.getInstance("RUR")), notNullValue());
		assertEquals(-100000, accFunds.get(Currency.getInstance("RUR")).getValue());
		
		assertThat(accFunds.get(Currency.getInstance("USD")), notNullValue());
		assertEquals(-66600, accFunds.get(Currency.getInstance("USD")).getValue());
		
	}
	
	@Test
	public void refillTest() {
		
		long accId = 1;
		Funds fundsRUR = new Funds(100000, Currency.getInstance("RUR"));
		Funds fundsUSD = new Funds(66600, Currency.getInstance("USD"));
		long sourceId = 1; 
		
		DomainRequestContext domainContext = DomainRequestContext.create(mDba);
		
		boolean res = OperationScripts.refill(domainContext, accId, fundsRUR, sourceId);		
		assertTrue(res);
		
		res = OperationScripts.refill(domainContext, accId, fundsUSD, sourceId);		
		assertTrue(res);
				
		////////////////////////////
		DomainRequestContext.create(mDba);
		GatewayRegistry gatewaysRegistry = DomainRequestContext.getInstance().getGatewayRegistry();
		
		AccountGateway account = (AccountGateway)gatewaysRegistry.get(Account.class);
		
		Account acc = (Account)account.findById(accId);
		
		Map<Currency, Funds> accFunds = acc.getFunds();
		
		assertEquals(2, accFunds.size());
		
		assertThat(accFunds.get(Currency.getInstance("RUR")), notNullValue());
		assertEquals(100000, accFunds.get(Currency.getInstance("RUR")).getValue());
		
		assertThat(accFunds.get(Currency.getInstance("USD")), notNullValue());
		assertEquals(66600, accFunds.get(Currency.getInstance("USD")).getValue());
		
	}
	
	@Test
	public void moveTest() {
		
		long accId1 = 1;
		long accId2 = 2;
		Funds fundsRUR = new Funds(30000, Currency.getInstance("RUR"));
		long sourceId = 1;
		
		DomainRequestContext domainContext = DomainRequestContext.create(mDba);
		
		boolean res = OperationScripts.move(domainContext, accId1, accId2, fundsRUR);		
		assertTrue(!res);
						
		res = OperationScripts.refill(domainContext, accId1, new Funds(100000, Currency.getInstance("RUR")), sourceId);
		assertTrue(res);
		
		res = OperationScripts.move(domainContext, accId1, accId2, fundsRUR);
		
		assertTrue(res);
		
		////////////////////////////
		DomainRequestContext.create(mDba);
		GatewayRegistry gatewaysRegistry = DomainRequestContext.getInstance().getGatewayRegistry();
		
		AccountGateway account = (AccountGateway)gatewaysRegistry.get(Account.class);
		
		Account acc1 = (Account)account.findById(accId1);
		Account acc2 = (Account)account.findById(accId2);
		
		Map<Currency, Funds> accFunds1 = acc1.getFunds();
		Map<Currency, Funds> accFunds2 = acc2.getFunds();
		
		assertEquals(1, accFunds1.size());
		assertEquals(1, accFunds2.size());

		assertThat(accFunds1.get(Currency.getInstance("RUR")), notNullValue());
		assertEquals(70000, accFunds1.get(Currency.getInstance("RUR")).getValue());		
		
		assertThat(accFunds2.get(Currency.getInstance("RUR")), notNullValue());
		assertEquals(30000, accFunds2.get(Currency.getInstance("RUR")).getValue());		
		
	}
	
}