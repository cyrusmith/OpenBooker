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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String[] getColumns() {
		// TODO Auto-generated method stub
		return null;
	}

}
