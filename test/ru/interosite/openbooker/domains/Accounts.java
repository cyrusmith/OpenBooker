package ru.interosite.openbooker.domains;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import ru.interosite.openbooker.datamodel.domain.Account;
import ru.interosite.openbooker.datamodel.domain.AccountType;
import ru.interosite.openbooker.datamodel.domain.Currency;
import ru.interosite.openbooker.datamodel.domain.EntitiesFactory;
import ru.interosite.openbooker.datamodel.domain.ExpenseType;
import ru.interosite.openbooker.datamodel.domain.Funds;
import ru.interosite.openbooker.datamodel.domain.IncomeSource;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThat;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class Accounts {

	@Test
	public void accOperations() {
		
		Account acc1 = EntitiesFactory.createAccount(AccountType.CASH);
		Account acc2 = EntitiesFactory.createAccount(AccountType.CREDIT_CARD);
		
		acc1.refill(new Funds(10000, Currency.RUR), new IncomeSource("Salary"));
		acc1.debit(new Funds(5000, Currency.RUR), new ExpenseType());
		
		acc1.moveTo(acc2, new Funds(5000, Currency.RUR));
		
	}
	
}