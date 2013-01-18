package ru.interosite.openbooker.datamodel.gateway;

import ru.interosite.openbooker.datamodel.domain.BaseEntity;
import ru.interosite.openbooker.datamodel.domain.Operation;
import ru.interosite.openbooker.datamodel.tables.OperationTableModel;
import ru.interosite.openbooker.datamodel.tables.TableModel;
import android.content.ContentValues;
import android.database.Cursor;

public class OperationGateway extends DatabaseGateway {

	@Override
	protected ContentValues doGetContentValues(BaseEntity entity) {
		if(!(entity instanceof Operation)) {
			throw new IllegalArgumentException("Entity is not an Operation");
		}
		
		ContentValues values = new ContentValues();
		
		Operation op = (Operation)entity;
		values.put(OperationTableModel.TYPE, op.getType().toString());
		values.put(OperationTableModel.DATETIME, op.getDateTime());
		values.put(OperationTableModel.OPERATION_DATA, op.getDataJson());
		
		return values;
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
