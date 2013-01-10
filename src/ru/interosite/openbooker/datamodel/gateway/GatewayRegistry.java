package ru.interosite.openbooker.datamodel.gateway;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import ru.interosite.openbooker.datamodel.DBAccess;

public class GatewayRegistry {
	
	private final DBAccess mDba;
	
	private final Map<Class<?>, DatabaseGateway> mGateways = new HashMap<Class<?>, DatabaseGateway>();
		
	public static final DatabaseGateway EMPTY_GATEWAY = new DatabaseGateway(null); 
	
	private void addDomainObjects() {
		mGateways.put(AccountGateway.class, EMPTY_GATEWAY);
	}
	
	public GatewayRegistry(DBAccess dba) {
		mDba = dba;
		addDomainObjects();
	}
		
	public DatabaseGateway get(Class<DatabaseGateway> clazz) {
		if(clazz==null) {
			throw new IllegalArgumentException();
		}
		DatabaseGateway gateway = mGateways.get(clazz);
		if(gateway==EMPTY_GATEWAY) {
			Constructor<? extends DatabaseGateway> constr;
			try {
				constr = clazz.getDeclaredConstructor(DBAccess.class);
				gateway = constr.newInstance(mDba);
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		return mGateways.get(clazz);
	}
	
}