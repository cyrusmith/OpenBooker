package ru.interosite.openbooker.domains;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import ru.interosite.openbooker.domains.accounts.Account;
import ru.interosite.openbooker.domains.accounts.ExpenseType;
import ru.interosite.openbooker.domains.accounts.IncomeSource;
import ru.interosite.openbooker.domains.funds.Currency;
import ru.interosite.openbooker.domains.funds.Funds;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThat;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class Accounts {

	@Test
	public void accOperations() {
		
		Account acc1 = new Account();
		Account acc2 = new Account();
		
		acc1.refill(new Funds(10000, Currency.RUR), new IncomeSource("Salary"));
		acc1.debit(new Funds(5000, Currency.RUR), new ExpenseType());
		
		acc1.moveTo(acc2, new Funds(5000, Currency.RUR));
		
	}
	
}