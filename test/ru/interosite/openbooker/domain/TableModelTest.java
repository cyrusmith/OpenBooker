package ru.interosite.openbooker.domain;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;

import ru.interosite.openbooker.datamodel.tables.AccountTypeTableModel;
import ru.interosite.openbooker.datamodel.tables.AccountsTableModel;
import ru.interosite.openbooker.datamodel.tables.OperationFactTableModel;
import ru.interosite.openbooker.datamodel.tables.TableModel;

import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class TableModelTest {
	@Test
	public void getModel() {
		TableModel model = TableModel.getModel(AccountsTableModel.class);
		assertThat(model, instanceOf(AccountsTableModel.class));
		assertThat(model.getTypeFor(AccountsTableModel.TITLE), equalTo("TEXT NOT NULL"));
	}
	
	@Test
	public void accountTypeTableModel() {
		TableModel model = TableModel.getModel(AccountTypeTableModel.class);
		assertEquals(5, model.getInsertsOnCreate().size());
	}
	
}
