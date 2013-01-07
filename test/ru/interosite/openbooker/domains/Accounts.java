package ru.interosite.openbooker.domains;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import ru.interosite.openbooker.datamodel.domain.Account;
import ru.interosite.openbooker.datamodel.domain.AccountType;
import ru.interosite.openbooker.datamodel.domain.CompoundAction;
import ru.interosite.openbooker.datamodel.domain.Currency;
import ru.interosite.openbooker.datamodel.domain.EntitiesFactory;
import ru.interosite.openbooker.datamodel.domain.ExpenseType;
import ru.interosite.openbooker.datamodel.domain.Funds;
import ru.interosite.openbooker.datamodel.domain.IncomeSource;
import ru.interosite.openbooker.datamodel.domain.UnsufficientFundsException;
import ru.interosite.openbooker.datamodel.mapper.DatabaseGateway;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertEquals;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class Accounts {

	@Test
	public void accOperations() {
		
		Account acc1 = EntitiesFactory.createAccount(AccountType.CASH, new Funds(100000, Currency.RUR));
		Account acc2 = EntitiesFactory.createAccount(AccountType.CREDIT_CARD, new Funds(100000, Currency.RUR));
		
		assertThat(acc1.getBalance(), notNullValue());
		assertThat(acc2.getBalance().size(), equalTo(1));
		
		acc1.debit(new Funds(50000, Currency.USD), new ExpenseType("Food"));
		
		assertThat(acc1.getBalance().size(), equalTo(2));
		
		acc1.refill(new Funds(20000, Currency.RUR), new IncomeSource("Salary"));
		
		assertThat(acc1.getBalance().size(), equalTo(2));
				
		try {
			acc1.moveTo(acc2, new Funds(50000, Currency.RUR));
		} catch (UnsufficientFundsException e) {
			throw new AssertionError("UnsufficientFundsException: " + e.getMessage());
		}
				
		assertEquals(70000, acc1.getBalance().get(Currency.RUR).getValue());
		assertEquals(150000, acc2.getBalance().get(Currency.RUR).getValue());
	}
		
	@Test(expected=UnsufficientFundsException.class)
	public void moveFunds() throws UnsufficientFundsException {
		
		Account acc1 = EntitiesFactory.createAccount(AccountType.CASH, new Funds(100000, Currency.RUR));
		Account acc2 = EntitiesFactory.createAccount(AccountType.CREDIT_CARD, new Funds(100000, Currency.USD));	
		acc1.moveTo(acc2, new Funds(10000, Currency.USD));
	}
	
	@Test
	public void hasFunds() throws UnsufficientFundsException {
		Account acc = EntitiesFactory.createAccount(AccountType.CASH, new Funds(100000, Currency.RUR));
		assertTrue(acc.hasFunds(new Funds(40000, Currency.RUR)));
		assertTrue(!acc.hasFunds(new Funds(1000, Currency.USD)));
	}
	
	@Test
	public void createPersistent() {
		DatabaseGateway gateway = DatabaseGateway.getInstance().init(Robolectric.application.getApplicationContext());
		CompoundAction.open(gateway);		
		Account acc = EntitiesFactory.createAccount(AccountType.CASH, new Funds(100000, Currency.RUR));		
		CompoundAction.execute();
	}
	
}