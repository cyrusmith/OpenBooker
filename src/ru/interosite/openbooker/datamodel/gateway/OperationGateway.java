package ru.interosite.openbooker.datamodel.gateway;

import ru.interosite.openbooker.datamodel.DBAccess;
import ru.interosite.openbooker.datamodel.domain.BaseEntity;
import ru.interosite.openbooker.datamodel.tables.OperationTableModel;
import ru.interosite.openbooker.datamodel.tables.TableModel;
import android.content.ContentValues;
import android.database.Cursor;

public class OperationGateway extends DatabaseGateway {

	public OperationGateway(DBAccess dba) {
		super(dba);
	}

	@Override
	protected ContentValues doGetContentValues(BaseEntity entity) {
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
		return TableModel.getModel(OperationTableModel.class);
	}
	
}
