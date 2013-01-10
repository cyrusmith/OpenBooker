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
import ru.interosite.openbooker.datamodel.domain.IOperation;
import ru.interosite.openbooker.datamodel.domain.IncomeSource;
import ru.interosite.openbooker.datamodel.persistent.EntitiesRegistry;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThat;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class Accounts {

	@Test
	public void accOperations() {		
		
		EntitiesRegistry.getInstance().setContext(Robolectric.application.getApplicationContext());		
		Account EntitiesRegistry.getInstance().getAccount();
		
	}
	
	@Test
	public void accountServiceSetInitialBalance() {
		Account cash = EntitiesFactory.createCashAccount();
		cash.cash
	}
	
}