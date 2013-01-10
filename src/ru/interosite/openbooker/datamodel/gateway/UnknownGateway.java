package ru.interosite.openbooker.datamodel.gateway;

import android.content.ContentValues;
import ru.interosite.openbooker.datamodel.DBAccess;
import ru.interosite.openbooker.datamodel.domain.BaseEntity;

public class UnknownGateway extends DatabaseGateway {

	public UnknownGateway(DBAccess dba) {
		super(dba);
	}

	@Override
	protected String getTableName() {
		return null;
	}

	@Override
	protected String[] getColumns() {
		return null;
	}
	
	@Override
	protected ContentValues getContentValues(BaseEntity entity) {
		return null;
	}
	
}
