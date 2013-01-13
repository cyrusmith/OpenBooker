package ru.interosite.openbooker.domain;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.interosite.openbooker.datamodel.DBAccess;
import ru.interosite.openbooker.datamodel.domain.Account;
import ru.interosite.openbooker.datamodel.domain.AccountType;
import ru.interosite.openbooker.datamodel.domain.Currency;
import ru.interosite.openbooker.datamodel.domain.EntitiesFactory;
import ru.interosite.openbooker.datamodel.domain.Funds;
import ru.interosite.openbooker.datamodel.domain.IncomeSource;
import ru.interosite.openbooker.datamodel.domain.Operation;
import ru.interosite.openbooker.datamodel.domain.OperationRefill;
import ru.interosite.openbooker.datamodel.gateway.DatabaseGateway;
import ru.interosite.openbooker.datamodel.gateway.GatewayRegistry;
import ru.interosite.openbooker.datamodel.tables.AccountTypeTableModel;
import ru.interosite.openbooker.datamodel.tables.AccountsTableModel;
import ru.interosite.openbooker.datamodel.tables.OperationFactTableModel;
import ru.interosite.openbooker.datamodel.tables.TableModel;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xtremelabs.robolectric.Robolectric;
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
	
	@Test
	public void compoundKey() {
		TableModel model = TableModel.getModel(OperationFactTableModel.class);
		assertTrue(model.isCompoundKey());
		assertThat(model.getCompoundKeyString(), equalTo("PRIMARY KEY (operation_id, category_id)"));
	}
}
