package ru.interosite.openbooker.datamodel.gateway;

import android.content.ContentValues;
import ru.interosite.openbooker.datamodel.DBAccess;
import ru.interosite.openbooker.datamodel.domain.BaseEntity;

public class AccountGateway extends DatabaseGateway {

	public static final String TABLE_NAME = "accounts";
	
	public AccountGateway(DBAccess dba) {
		super(dba);
	}

	@Override
	protected String getTableName() {
		return TABLE_NAME;
	}

	@Override
	protected String[] getColumns() {
		return new String[]{};
	}

}