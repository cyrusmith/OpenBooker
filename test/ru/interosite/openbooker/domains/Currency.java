package ru.interosite.openbooker.domains;

import java.awt.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.CoreMatchers.not;

import android.content.Context;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class Currency {

	@Test(expected=IllegalOperation.class)
	public void illegalFundsPlus() {
		Funds funds1 = new Funds(66500, Currency.getCurrency(Currency.RUR));
		Funds funds2 = new Funds(66500, Currency.getCurrency(Currency.USD));
		Funds fundsTotal = funds1.plus(funds2);	
	}
	
	@Test
	public void calculateAccountsSum() {
		
		Account accInRUR = new Account();
		Account accInUSD = new Account();
		
		Funds fundsAcc1 = accInRUR.getFunds();
		Funds fundsAcc2 = accInUSD.getFunds();
		
		Funds sum = fundsAcc1.convert(fundsAcc2.getCurrency()).plus(fundsAcc2);
				
	}
	
	@Test
	public void currencyInstances() {
		assertThat(Currency.getCurrency(Currency.RUR), sameInstance(Currency.getCurrency(Currency.RUR)));
		assertThat(Currency.getCurrency(Currency.RUR), equalTo(Currency.getCurrency(Currency.RUR)));
		
		assertThat(Currency.getCurrency(Currency.RUR), not(sameInstance(Currency.getCurrency(Currency.USD))));		
		assertThat(Currency.getCurrency(Currency.RUR), not(equalTo(Currency.getCurrency(Currency.USD))));		
	}
	
	@Test
	public void setHomeCurrency() {
		Currency.setHomeCurrency(Currency.getCurrency(Currency.RUR));
		assertThat(Currency.getHomeCurrency(), is(Currency.class));
		assertThat(Currency.getHomeCurrency(), equalTo(Currency.getCurrency(Currency.RUR)));
	}
	
	@Test
	public void calculateExpenseCategoriesSum() {
		
		ExpenseCategory cat1 = new ExpenseCategory();
		ExpenseCategory cat2 = new ExpenseCategory();
		
		Funds funds1 = cat1.getTotalFundsInHomeCurrency();
		Funds funds2 = cat2.getTotalFundsInHomeCurrency();
		
		assertEquals(funds1.getCurrency(), funds2.getCurrency());
		
		assertThat(cat1.getFundsPerCurrencies(), is(List.class));
		
	}
	
	@Test
	public void calculateIncomeCategoriesSum() {
		
		IncomeCategory cat1 = new IncomeCategory();
		IncomeCategory cat2 = new IncomeCategory();
		
		Funds funds1 = cat1.getTotalFundsInHomeCurrency();
		Funds funds2 = cat2.getTotalFundsInHomeCurrency();
		
		assertThat(funds1.getCurrency(), equalTo(funds2.getCurrency()));
		
		assertThat(cat1.getFundsPerCurrencies(), is(List.class));
		
	}
	
}