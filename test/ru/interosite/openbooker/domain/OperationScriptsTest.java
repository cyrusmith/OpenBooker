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
		
		Account acc = entitiesFactory.createAccount(AccountType.CASH, Funds.EMPTY);
		AccountGateway accGateway = (AccountGateway)gatewaysRegistry.get(Account.class);
		
		long newAccId = accGateway.insert(acc);
		assertEquals(1, newAccId);
		
		ExpenseType extType = entitiesFactory.createExpenseType("≈‰‡", ExpenseType.ROOT);
		ExpenseTypeGateway expGateway = (ExpenseTypeGateway)gatewaysRegistry.get(ExpenseType.class);
		long newExtTypeId = expGateway.insert(extType);
		
		assertEquals(1, newExtTypeId);
		
	}
	
	@Before
	public void setUp() {
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
		
		boolean res = OperationScripts.debit(mDba, accId, fundsRUR, expenceTypeId);		
		assertTrue(res);
		
		res = OperationScripts.debit(mDba, accId, fundsUSD, expenceTypeId);		
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
	
}