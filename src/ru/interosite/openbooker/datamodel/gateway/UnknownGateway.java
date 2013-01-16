package ru.interosite.openbooker.datamodel.gateway;

import ru.interosite.openbooker.datamodel.DBAccess;
import ru.interosite.openbooker.datamodel.domain.BaseEntity;
import ru.interosite.openbooker.datamodel.tables.TableModel;
import android.content.ContentValues;
import android.database.Cursor;

public class UnknownGateway extends DatabaseGateway {

	public UnknownGateway(DBAccess dba) {
		super(dba);
	}

	@Override
	protected ContentValues doGetContentValues(BaseEntity entity) {
		return null;
	}
	
	@Override
	protected BaseEntity loadEntity(long id, Cursor c) {
		return null;
	}

	@Override
	protected TableModel getTableModel() {
		return null;
	}	
	
}