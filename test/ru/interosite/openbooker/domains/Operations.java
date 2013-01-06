package ru.interosite.openbooker.domains;

import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.equalTo;

import ru.interosite.openbooker.datamodel.domain.Account;
import ru.interosite.openbooker.datamodel.domain.AccountType;
import ru.interosite.openbooker.datamodel.domain.Currency;
import ru.interosite.openbooker.datamodel.domain.EntitiesFactory;
import ru.interosite.openbooker.datamodel.domain.ExpenseType;
import ru.interosite.openbooker.datamodel.domain.Funds;
import ru.interosite.openbooker.datamodel.domain.Operation;
import ru.interosite.openbooker.datamodel.domain.OperationDebit;

public class Operations {
	
	@Test
	public void create() {
		
		Account acc = EntitiesFactory.createAccount(AccountType.CASH, new Funds(100000, Currency.RUR));
		acc.debit(new Funds(30000, Currency.RUR), new ExpenseType("Gas"));
		Operation op = acc.getLastOperation();		
		assertThat(op, instanceOf(OperationDebit.class));
		
		acc.debit(new Funds(30000, Currency.RUR), new ExpenseType("Gas"));
		assertThat(acc.getOperations(), instanceOf(List.class));
		assertThat(acc.getOperations().size(), equalTo(2));
		
	}
	
}