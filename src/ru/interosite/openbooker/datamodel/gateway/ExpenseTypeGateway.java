package ru.interosite.openbooker.datamodel.gateway;

import ru.interosite.openbooker.datamodel.DBAccess;
import ru.interosite.openbooker.datamodel.domain.BaseEntity;
import ru.interosite.openbooker.datamodel.tables.AccountsTableModel;
import ru.interosite.openbooker.datamodel.tables.ExpenseTypeTableModel;
import ru.interosite.openbooker.datamodel.tables.TableModel;
import android.content.ContentValues;
import android.database.Cursor;

public class ExpenseTypeGateway extends DatabaseGateway {

	public ExpenseTypeGateway(DBAccess dba) {
		super(dba);
	}

	@Override
	protected ContentValues getContentValues(BaseEntity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected BaseEntity loadEntity(long id, Cursor c) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected TableModel getTableModel() {
		return TableModel.getModel(ExpenseTypeTableModel.class);
	}	
	
}
