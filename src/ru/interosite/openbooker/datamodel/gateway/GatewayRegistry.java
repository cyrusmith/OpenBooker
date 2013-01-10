package ru.interosite.openbooker.datamodel.gateway;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import ru.interosite.openbooker.datamodel.DBAccess;
import ru.interosite.openbooker.datamodel.domain.Account;
import ru.interosite.openbooker.datamodel.domain.BaseEntity;
import ru.interosite.openbooker.datamodel.domain.ExpenseType;
import ru.interosite.openbooker.datamodel.domain.IncomeSource;
import ru.interosite.openbooker.datamodel.domain.OperationDebit;
import ru.interosite.openbooker.datamodel.domain.OperationRefill;

public class GatewayRegistry {
	
	private final DBAccess mDba;
	
	private final static Map<Class<? extends BaseEntity>, Class<? extends DatabaseGateway>> mEntityGatewayMap = new HashMap<Class<? extends BaseEntity>, Class<? extends DatabaseGateway>>();
		
	private static void fillEntityGatewayMap() {
		mEntityGatewayMap.put(Account.class, AccountGateway.class);
		mEntityGatewayMap.put(OperationRefill.class, AccountGateway.class);
		mEntityGatewayMap.put(OperationDebit.class, AccountGateway.class);
		mEntityGatewayMap.put(IncomeSource.class, AccountGateway.class);
		mEntityGatewayMap.put(ExpenseType.class, AccountGateway.class);
	}
	
	static {
		fillEntityGatewayMap();
	}
	
	private final Map<Class<? extends BaseEntity>, DatabaseGateway> mGateways = new HashMap<Class<? extends BaseEntity>, DatabaseGateway>();
	
	public GatewayRegistry(DBAccess dba) {
		mDba = dba;
		for(Class<? extends BaseEntity> entityClass : mEntityGatewayMap.keySet()) {
			mGateways.put(entityClass, DatabaseGateway.UNKNOWN_GATEWAY);
		}
	}
		
	public DatabaseGateway get(Class<? extends BaseEntity> entityClass) {
		if(entityClass==null) {
			throw new IllegalArgumentException();
		}
		
		Class<? extends DatabaseGateway> gatewayClass = mEntityGatewayMap.get(entityClass);
		
		if(gatewayClass==null) {
			throw new IllegalArgumentException("Class "  + entityClass.getName() + " has no gateway assinged");
		}
		
		DatabaseGateway gateway = mGateways.get(entityClass);
		if(gateway==DatabaseGateway.UNKNOWN_GATEWAY) {
			Constructor<? extends DatabaseGateway> constr;
			try {
				constr = gatewayClass.getDeclaredConstructor(DBAccess.class);
				gateway = constr.newInstance(mDba);
				mGateways.put(entityClass, gateway);
			} catch (Exception e) {
				throw new RuntimeException("Cannot instantiate gateway class");
			}
	
		}
		return gateway;
	}

	
}