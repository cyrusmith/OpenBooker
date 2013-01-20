package ru.interosite.openbooker.datamodel.gateway;

import ru.interosite.openbooker.ApplicationConfig;
import ru.interosite.openbooker.datamodel.domain.BaseEntity;
import ru.interosite.openbooker.datamodel.domain.IncomeSource;
import ru.interosite.openbooker.datamodel.tables.IncomeSourceTableModel;
import ru.interosite.openbooker.datamodel.tables.TableModel;
import android.content.ContentValues;
import android.database.Cursor;

public class IncomeSourceGateway extends DatabaseGateway {

	@Override
	protected Class<? extends BaseEntity> getEntityClass() {
		return IncomeSource.class;
	}
	
	@Override
	protected ContentValues doGetContentValues(BaseEntity entity) {
		
		IncomeSource source = (IncomeSource)entity;
		
		ContentValues values = new ContentValues();
		values.put(IncomeSourceTableModel.TITLE, source.getTitle());
		
		return values;
	}
	
	@Override
	protected BaseEntity loadEntity(long id, Cursor c) {
		String title = c.getString(c.getColumnIndex(IncomeSourceTableModel.TITLE));
		if(title==null || "".equals(title)) {
			title = ApplicationConfig.getInstance().getString(BaseEntity.UNTITLED);
		}
		IncomeSource source = mEntitiesFactory.createIncomeSource(title);
		source.setId(id);
		return source;
	}
	
	@Override
	protected TableModel getTableModel() {
		return TableModel.getModel(IncomeSourceTableModel.class);
	}
	
}