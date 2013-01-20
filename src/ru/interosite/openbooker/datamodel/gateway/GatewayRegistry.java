package ru.interosite.openbooker.datamodel.gateway;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import ru.interosite.openbooker.datamodel.domain.Account;
import ru.interosite.openbooker.datamodel.domain.BaseEntity;
import ru.interosite.openbooker.datamodel.domain.ExpenseType;
import ru.interosite.openbooker.datamodel.domain.IncomeSource;
import ru.interosite.openbooker.datamodel.domain.Operation;

public class GatewayRegistry {
	
	private final static Map<Class<? extends BaseEntity>, Class<? extends DatabaseGateway>> mEntityGatewayMap = new HashMap<Class<? extends BaseEntity>, Class<? extends DatabaseGateway>>();
		
	private static void fillEntityGatewayMap() {
		mEntityGatewayMap.put(Account.class, AccountGateway.class);
		mEntityGatewayMap.put(Operation.class, OperationGateway.class);
		mEntityGatewayMap.put(IncomeSource.class, IncomeSourceGateway.class);
		mEntityGatewayMap.put(ExpenseType.class, ExpenseTypeGateway.class);
	}
	
	static {
		fillEntityGatewayMap();
	}
	
	private final Map<Class<? extends BaseEntity>, DatabaseGateway> mGateways = new HashMap<Class<? extends BaseEntity>, DatabaseGateway>();
	
	public GatewayRegistry() {
		for(Class<? extends BaseEntity> entityClass : mEntityGatewayMap.keySet()) {
			mGateways.put(entityClass, UnknownGateway.getInstance());
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
		if(gateway==UnknownGateway.getInstance()) {
			Constructor<? extends DatabaseGateway> constr;
			try {
				constr = gatewayClass.getDeclaredConstructor();
				gateway = constr.newInstance();
				mGateways.put(entityClass, gateway);
			} catch (Exception e) {
				throw new RuntimeException("Cannot instantiate gateway class " + gatewayClass.getName() +  ": " + e.getClass().getName());
			}	
		}
		return gateway;
	}
}