package ru.interosite.openbooker.datamodel.gateway;

import org.slf4j.LoggerFactory;

import ru.interosite.openbooker.datamodel.domain.BaseEntity;
import ru.interosite.openbooker.datamodel.domain.Operation;
import ru.interosite.openbooker.datamodel.tables.OperationTableModel;
import ru.interosite.openbooker.datamodel.tables.TableModel;
import android.content.ContentValues;
import android.database.Cursor;

public class OperationGateway extends DatabaseGateway {

	private static final String TAG = "ru.interosite.openbooker.datamodel.gateway.OperationGateway";
	
	@Override
	protected Class<? extends BaseEntity> getEntityClass() {
		return Operation.class;
	}
	
	@Override
	protected ContentValues doGetContentValues(BaseEntity entity) {
		
		ContentValues values = new ContentValues();
		
		Operation op = (Operation)entity;
		values.put(OperationTableModel.TYPE, op.getType().toString());
		values.put(OperationTableModel.DATETIME, op.getDateTime());
		
		String jsonStr = "{}";
		try {
			jsonStr = op.getDataJson().toString();
		}
		catch(Exception e) {
			LoggerFactory.getLogger(TAG).warn("Error getting json data from operation {}: {}", op.getClass().getName(), e.getClass().getName()); 
		}
		
		values.put(OperationTableModel.OPERATION_DATA, jsonStr);
		
		return values;
	}

	@Override
	protected BaseEntity loadEntity(long id, Cursor c) {
		return null;
	}

	@Override
	protected TableModel getTableModel() {
		return TableModel.getModel(OperationTableModel.class);
	}
		
}