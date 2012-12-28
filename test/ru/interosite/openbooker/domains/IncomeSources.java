package ru.interosite.openbooker.domains;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.awt.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import ru.interosite.openbooker.datamodel.domain.IncomeSource;

import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class IncomeSources {

	@Test
	public void create() {
		IncomeSource source = new IncomeSource("Salary");
		assertThat(source.getTitle(), equalTo("Salary"));
	}
	
}