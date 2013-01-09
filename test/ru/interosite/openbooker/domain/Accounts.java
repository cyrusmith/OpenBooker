package ru.interosite.openbooker.domain;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.interosite.openbooker.datamodel.domain.Account;
import ru.interosite.openbooker.datamodel.domain.AccountType;
import ru.interosite.openbooker.datamodel.domain.Currency;
import ru.interosite.openbooker.datamodel.domain.Funds;
import ru.interosite.openbooker.datamodel.domain.IncomeSource;

import android.content.ContentValues;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class Accounts {
	
	@Test
	public void createAccount() {
		//Params: 
		long accTypeId = 1; 
		String title = "Wallet";
		Funds initialFunds = new Funds(100000, Currency.RUR); 
				
		GatewayRegistry gateways = GatewayRegistry.create(Robolectric.application.getApplicationContext());
		EntitiesRegistry registry = EntitiesRegistry.create(gateways);
		
		AccountType accType = (AccountType)registry.get(AccountType.class, accTypeId);
		
		Account acc = EntitiesFactory.createAccount(accType, title, initialFunds);
				
		DatabaseGateway accountGateway = gateways.get(Account.class);
		assertThat(accountGateway, notNullValue());
		
		int newAccId = accountGateway.insert(acc);
		assertTrue(newAccId > 0);
		
	}
	
	@Test
	public void refillAccount() {
		
		//Params
		long accId = 1;
		Funds funds = new Funds(3306000, Currency.RUR);
		long incomeSourceId = 2; 
		
		GatewayRegistry gateways = GatewayRegistry.create(Robolectric.application.getApplicationContext());	
		EntitiesRegistry registry = EntitiesRegistry.create(gateways);
		
		Account account = (Account)registry.get(Account.class, accId);
		IncomeSource source = (IncomeSource)registry.get(IncomeSource.class, incomeSourceId);
		
		Operation operation = EntitiesRegistry.createRefillOperation(account, source, funds);
		
		account.addFunds(funds);
		
		DatabaseGateway accountGateway = gateways.get(Account.class);
		DatabaseGateway operationGateway = gateways.get(OperationRefill.class);
		
		
		int numUpdated = accountGateway.update(account);
		int newInserted = operationGateway.insert(operation);
		
		assertTrue(numUpdated == 1);
		assertTrue(newInserted  > 0);
	}
	
}