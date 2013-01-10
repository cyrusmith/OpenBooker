package ru.interosite.openbooker.datamodel.gateway;

import ru.interosite.openbooker.datamodel.DBAccess;

public class DatabaseGateway {
	protected DBAccess mDba = null; 
	public DatabaseGateway(DBAccess dba) {
		mDba = dba;
	}
}