package ru.interosite.openbooker.gateway;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import ru.interosite.openbooker.datamodel.DBAccess;
import ru.interosite.openbooker.datamodel.domain.Account;
import ru.interosite.openbooker.datamodel.domain.IncomeSource;
import ru.interosite.openbooker.datamodel.gateway.AccountGateway;
import ru.interosite.openbooker.datamodel.gateway.DatabaseGateway;
import ru.interosite.openbooker.datamodel.gateway.GatewayRegistry;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class GatewayRegistryTest {
	
	@Test
	public void get() {
		DBAccess dba = new DBAccess(Robolectric.application.getApplicationContext());
		GatewayRegistry reg = dba.getGatewayRegistry();
		DatabaseGateway gway = reg.get(Account.class);
		assertThat(gway, instanceOf(AccountGateway.class));
	}
	
	public void getIsSameInstance() {
		DBAccess dba = new DBAccess(Robolectric.application.getApplicationContext());
		GatewayRegistry reg = dba.getGatewayRegistry();
		DatabaseGateway gway1 = reg.get(IncomeSource.class);
		DatabaseGateway gway2 = reg.get(IncomeSource.class);
		
		assertThat(gway1, sameInstance(gway2));
	}
}