package ru.interosite.openbooker.mappers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import ru.interosite.openbooker.datamodel.domain.Account;
import ru.interosite.openbooker.datamodel.domain.AccountType;
import ru.interosite.openbooker.datamodel.domain.BaseEntity;
import ru.interosite.openbooker.datamodel.domain.Currency;
import ru.interosite.openbooker.datamodel.domain.EntitiesFactory;
import ru.interosite.openbooker.datamodel.domain.Funds;
import ru.interosite.openbooker.datamodel.domain.IMapper;
import ru.interosite.openbooker.datamodel.mapper.MapperRegistry;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class AccountMappers {
	
	@Test
	public void findAccount() {
		
		BaseEntity acc = EntitiesFactory.createAccount(AccountType.CASH, new Funds(100000, Currency.RUR));
		assertThat(acc, instanceOf(Account.class));
		
		IMapper accMapper = MapperRegistry.getInstance().get(Account.class);
		accMapper.insert(acc);
		BaseEntity accSaved = accMapper.find(acc.getId());
		assertThat(accSaved, instanceOf(Account.class));
		assertThat(accSaved.getId(), equalTo(acc.getId()));
		
		//TODO check from db
	}
	
}