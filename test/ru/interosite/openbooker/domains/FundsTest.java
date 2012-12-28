package ru.interosite.openbooker.domains;

import org.junit.Test;
import org.junit.runner.RunWith;

import ru.interosite.openbooker.datamodel.domain.Currency;
import ru.interosite.openbooker.datamodel.domain.Funds;
import ru.interosite.openbooker.datamodel.domain.IllegalOperation;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;

import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class FundsTest {

	@Test
	public void createFunds() {
		Funds funds1 = new Funds(66500, Currency.RUR);
		assertEquals(funds1.getValue(), 66500);
		assertThat(funds1.getCurrency(), equalTo(Currency.RUR));
	}

	@Test
	public void fundsPlus() {
		Funds funds1 = new Funds(-10000, Currency.RUR);
		Funds funds2 = new Funds(20000, Currency.RUR);
		Funds fundsTotal = funds1.plus(funds2);

		assertThat(fundsTotal, not(sameInstance(funds1)));
		assertThat(fundsTotal, not(sameInstance(funds2)));

		assertEquals(fundsTotal.getValue(), 10000);
	}

	@Test
	public void fundsPlusNull() {
		Funds funds1 = new Funds(20000, Currency.RUR);
		Funds resFunds = funds1.plus(null);
		assertEquals(resFunds.getValue(), 20000);
		assertEquals(resFunds.getCurrency(), Currency.RUR);
	}

	@Test
	public void fundsCovertSameInstancePlus() {
		Funds funds1 = new Funds(10000, Currency.RUR);
		assertThat(funds1.convertTo(Currency.RUR), sameInstance(funds1));
	}

	@Test
	public void fundsCurrenciesConvertion() {
		Funds funds1 = new Funds(66500, Currency.RUR);
		Funds funds2 = new Funds(66500, Currency.USD);
		funds1.convertTo(Currency.USD).plus(funds2);
	}

	@Test(expected = IllegalOperation.class)
	public void illegalFundsPlus() {
		Funds funds1 = new Funds(66500, Currency.RUR);
		Funds funds2 = new Funds(66500, Currency.USD);
		funds1.plus(funds2);
	}

}