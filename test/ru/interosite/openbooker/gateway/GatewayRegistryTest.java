package ru.interosite.openbooker.gateway;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;

import ru.interosite.openbooker.datamodel.DBAccess;
import ru.interosite.openbooker.datamodel.domain.Account;
import ru.interosite.openbooker.datamodel.domain.Currency;
import ru.interosite.openbooker.datamodel.domain.EntitiesFactory;
import ru.interosite.openbooker.datamodel.domain.Funds;
import ru.interosite.openbooker.datamodel.domain.IncomeSource;
import ru.interosite.openbooker.datamodel.domain.Operation;
import ru.interosite.openbooker.datamodel.domain.OperationRefill;
import ru.interosite.openbooker.datamodel.gateway.AccountGateway;
import ru.interosite.openbooker.datamodel.gateway.DatabaseGateway;
import ru.interosite.openbooker.datamodel.gateway.GatewayRegistry;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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