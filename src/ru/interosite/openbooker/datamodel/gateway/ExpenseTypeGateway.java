package ru.interosite.openbooker.datamodel.gateway;

import ru.interosite.openbooker.datamodel.domain.BaseEntity;
import ru.interosite.openbooker.datamodel.domain.ExpenseType;
import ru.interosite.openbooker.datamodel.tables.ExpenseTypeTableModel;
import ru.interosite.openbooker.datamodel.tables.TableModel;
import android.content.ContentValues;
import android.database.Cursor;

public class ExpenseTypeGateway extends DatabaseGateway {

	@Override
	protected Class<? extends BaseEntity> getEntityClass() {
		return ExpenseType.class;
	}
	
	@Override
	protected ContentValues doGetContentValues(BaseEntity entity) {
		ExpenseType type = (ExpenseType)entity;
		ContentValues values = new ContentValues();
		values.put(ExpenseTypeTableModel.TITLE, type.getTitle());
		
		long parentId = 0;
		if(type.getParent()!=ExpenseType.ROOT) {
			parentId = type.getParent().getId();
		}
		values.put(ExpenseTypeTableModel.PARENT_TYPE_ID, parentId);
		return values;
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