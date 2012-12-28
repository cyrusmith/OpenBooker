package ru.interosite.openbooker.domains;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import ru.interosite.openbooker.datamodel.domain.ExpenseType;

import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class ExpenseTypes {

	@Test
	public void createNoTitle() {
		ExpenseType type = new ExpenseType();
		assertThat(type.getTitle(), nullValue());
	}
	
	@Test
	public void createWithTitle() {
		ExpenseType type = new ExpenseType("Поездка в сочи");
		assertThat(type.getTitle(), equalTo("Поездка в сочи"));
	}
	
}