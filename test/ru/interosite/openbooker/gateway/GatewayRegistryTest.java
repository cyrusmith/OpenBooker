package ru.interosite.openbooker.gateway;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.interosite.openbooker.ApplicationConfig;
import ru.interosite.openbooker.datamodel.DBAccess;
import ru.interosite.openbooker.datamodel.DomainRequestContext;
import ru.interosite.openbooker.datamodel.domain.Account;
import ru.interosite.openbooker.datamodel.domain.IncomeSource;
import ru.interosite.openbooker.datamodel.gateway.AccountGateway;
import ru.interosite.openbooker.datamodel.gateway.DatabaseGateway;
import ru.interosite.openbooker.datamodel.gateway.GatewayRegistry;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class GatewayRegistryTest {
	
	
	@Before
	public void setUp() {
		System.setProperty("robolectric.logging", "stdout");
		ApplicationConfig.getInstance().init(Robolectric.application.getApplicationContext());
		DomainRequestContext.create(new DBAccess(Robolectric.application.getApplicationContext()));
	}
	
	@Test
	public void get() {		
		GatewayRegistry reg = DomainRequestContext.getInstance().getGatewayRegistry();
		DatabaseGateway gway = reg.get(Account.class);
		assertThat(gway, instanceOf(AccountGateway.class));
	}
	
	public void getIsSameInstance() {
		GatewayRegistry reg = DomainRequestContext.getInstance().getGatewayRegistry();
		DatabaseGateway gway1 = reg.get(IncomeSource.class);
		DatabaseGateway gway2 = reg.get(IncomeSource.class);
		
		assertThat(gway1, sameInstance(gway2));
	}
}