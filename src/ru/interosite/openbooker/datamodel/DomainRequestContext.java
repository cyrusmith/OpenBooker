package ru.interosite.openbooker.datamodel;

import ru.interosite.openbooker.datamodel.domain.EntitiesFactory;
import ru.interosite.openbooker.datamodel.gateway.GatewayRegistry;

public class DomainRequestContext {

	private final static ThreadLocal<DomainRequestContext> mInstance = new ThreadLocal<DomainRequestContext>();
	
	public static DomainRequestContext getInstance() {
		return mInstance.get();
	}
	
	public static DomainRequestContext create(DBAccess dba) {
		mInstance.set(new DomainRequestContext(dba));
		return mInstance.get();
	}
		
	private final DBAccess mDba;
	private final GatewayRegistry mGatewayRegistry;
	private final EntitiesFactory mEntitiesFactory;
	
	private DomainRequestContext(DBAccess dba) {
		if(dba==null) {
			throw new IllegalArgumentException("Dba not set for DomainRequestContext");
		}
		mDba = dba;
		mGatewayRegistry = new GatewayRegistry();
		mEntitiesFactory = new EntitiesFactory();
	}
	
	public DBAccess getDba() {
		return mDba;
	}
	
	public EntitiesFactory getEntitiesFactory() {
		return mEntitiesFactory;
	}
	
	public GatewayRegistry getGatewayRegistry() {
		return mGatewayRegistry;
	}
	
}