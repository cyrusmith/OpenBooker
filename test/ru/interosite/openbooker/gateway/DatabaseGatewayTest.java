package ru.interosite.openbooker.gateway;


import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.Currency;
import java.util.Map;

import org.hamcrest.core.IsSame;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.interosite.openbooker.ApplicationConfig;
import ru.interosite.openbooker.datamodel.DBAccess;
import ru.interosite.openbooker.datamodel.DomainRequestContext;
import ru.interosite.openbooker.datamodel.OperationScripts;
import ru.interosite.openbooker.datamodel.domain.Account;
import ru.interosite.openbooker.datamodel.domain.AccountType;
import ru.interosite.openbooker.datamodel.domain.BaseEntity;
import ru.interosite.openbooker.datamodel.domain.EntitiesFactory;
import ru.interosite.openbooker.datamodel.domain.ExpenseType;
import ru.interosite.openbooker.datamodel.domain.Funds;
import ru.interosite.openbooker.datamodel.domain.IncomeSource;
import ru.interosite.openbooker.datamodel.domain.Operation;
import ru.interosite.openbooker.datamodel.domain.OperationRefill;
import ru.interosite.openbooker.datamodel.domain.Operation.OperationType;
import ru.interosite.openbooker.datamodel.domain.UnknownEntity;
import ru.interosite.openbooker.datamodel.gateway.AccountGateway;
import ru.interosite.openbooker.datamodel.gateway.DatabaseGateway;
import ru.interosite.openbooker.datamodel.gateway.ExpenseTypeGateway;
import ru.interosite.openbooker.datamodel.gateway.GatewayRegistry;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class DatabaseGatewayTest {
	
	private DBAccess mDba = null;
	
	@Before
	public void setUp() {
		System.setProperty("robolectric.logging", "stdout");
		mDba = new DBAccess(Robolectric.application.getApplicationContext());
		ApplicationConfig.getInstance().init(Robolectric.application.getApplicationContext());
		DomainRequestContext.create(mDba);
	}	

	@Test
	public void testFindFindByIdSameInstance() {
		Account acc = DomainRequestContext.getInstance().getEntitiesFactory().createAccount(AccountType.DEBIT_CARD, "VTB24", new Funds(100000, Currency.getInstance("RUR")));
		long newAccId = DomainRequestContext.getInstance().getGatewayRegistry().get(Account.class).insert(acc);
		
		assertEquals(1, newAccId);
		
		DomainRequestContext.create(mDba);
		Account acc1 = (Account)DomainRequestContext.getInstance().getGatewayRegistry().get(Account.class).findById(newAccId);
		Account acc2 = (Account)DomainRequestContext.getInstance().getGatewayRegistry().get(Account.class).findById(newAccId);
		
		assertFalse(acc==acc1);
		assertThat(acc1, sameInstance(acc2));
		assertEquals("VTB24", acc1.getTitle());
	}	
	
	@Test
	public void testInsertFindByIdSameInstance() {
		
		Account acc = DomainRequestContext.getInstance().getEntitiesFactory().createAccount(AccountType.DEBIT_CARD, "VTB24", new Funds(100000, Currency.getInstance("RUR")));
		DomainRequestContext.getInstance().getGatewayRegistry().get(Account.class).insert(acc);
		Account acc1 = (Account)DomainRequestContext.getInstance().getGatewayRegistry().get(Account.class).findById(1);
		assertThat(acc, sameInstance(acc1));
		assertThat(acc.getId(), equalTo(1L));
	}
	
	@Test
	public void testUpdateFindByIdSameInstance() {
		
		Account acc = DomainRequestContext.getInstance().getEntitiesFactory().createAccount(AccountType.DEBIT_CARD, "VTB24", new Funds(100000, Currency.getInstance("RUR")));
		long newAccId = DomainRequestContext.getInstance().getGatewayRegistry().get(Account.class).insert(acc);
		assertEquals(1, newAccId);
		
		DomainRequestContext.create(mDba);
		
		Account acc1 = (Account)DomainRequestContext.getInstance().getGatewayRegistry().get(Account.class).findById(newAccId);
		acc1.setTitle("VTB24 Salary");
		DomainRequestContext.getInstance().getGatewayRegistry().get(Account.class).update(acc1);
		Account accUpd = (Account)DomainRequestContext.getInstance().getGatewayRegistry().get(Account.class).findById(acc1.getId());
		assertThat(acc1, sameInstance(accUpd));
		
	}	
	
	@Test
	public void testDeleteFindById() {
		
		Account acc = DomainRequestContext.getInstance().getEntitiesFactory().createAccount(AccountType.DEBIT_CARD, "VTB24", new Funds(100000, Currency.getInstance("RUR")));
		DomainRequestContext.getInstance().getGatewayRegistry().get(Account.class).insert(acc);
		Account acc1 = (Account)DomainRequestContext.getInstance().getGatewayRegistry().get(Account.class).findById(1);
		assertThat(acc, sameInstance(acc1));
		assertThat(acc.getId(), equalTo(1L));
		
		DomainRequestContext.getInstance().getGatewayRegistry().get(Account.class).delete(acc1);
		assertThat(acc1.getId(), nullValue());
		
		BaseEntity acc2 = DomainRequestContext.getInstance().getGatewayRegistry().get(Account.class).findById(1);
		
		assertThat(acc2, instanceOf(UnknownEntity.class));
	}	
	
}