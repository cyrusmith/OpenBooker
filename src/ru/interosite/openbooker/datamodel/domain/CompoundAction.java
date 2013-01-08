package ru.interosite.openbooker.datamodel.domain;

import java.util.ArrayList;
import java.util.List;

import ru.interosite.openbooker.datamodel.mapper.DatabaseActionException;
import ru.interosite.openbooker.datamodel.mapper.MapperRegistry;


public class CompoundAction {
	
	private static ThreadLocal<CompoundAction> mAction = new ThreadLocal<CompoundAction>() {
		protected CompoundAction initialValue() {
			return new CompoundAction();
		};
	};
	
	public static void open() {
		mAction.set(new CompoundAction());
	}
	
	public static CompoundAction getCurrentAction() {
		return mAction.get();
	}
	
	public static boolean execute() {
		return mAction.get().executeTransaction();
	}
	
	private List<BaseEntity> mNewEntities = new ArrayList<BaseEntity>();
	private List<BaseEntity> mDirtyEntities = new ArrayList<BaseEntity>();
	private List<BaseEntity> mRemoveEntities = new ArrayList<BaseEntity>();
	
	private CompoundAction() {
	}	
	
	public void addNew(BaseEntity entity) {
		
		if(!isValidForAdd(entity)) {
			return;
		}
		
		mNewEntities.add(entity);
	}
		
	public void addDirty(BaseEntity entity) {
		if(!isValidForAdd(entity)) {
			return;
		}
		mDirtyEntities.add(entity);
	}
	
	public void addRemove(BaseEntity entity) {
		if(!isValidForAdd(entity)) {
			return;
		}
		mRemoveEntities.add(entity);
	}
	
	private boolean isValidForAdd(BaseEntity entity) {
		if(entity==null) {
			return false;
		}
		
		if(mNewEntities.contains(entity)) {
			return false;
		}
		
		if(mRemoveEntities.contains(entity)) {
			return false;
		}
		
		if(mDirtyEntities.contains(entity)) {
			return false;
		}		
		return true;
	}
	
	private boolean executeTransaction() {
		try {
			MapperRegistry.getInstance().transactionBegin();
			doInserts();
			doUpdates();
			doDeletes();
			MapperRegistry.getInstance().transactionSuccessful();
			return true;
		}
		catch(Exception e) {
			return false;
		}
		finally {
			MapperRegistry.getInstance().transactionEnd();
		}		
	}
	
	private void doInserts() throws DatabaseActionException {
		for(BaseEntity entity : mNewEntities) {
			MapperRegistry.getInstance().get(entity.getClass()).insert(entity);
		}
	}
	
	private void doUpdates() throws DatabaseActionException {
		for(BaseEntity entity : mDirtyEntities) {
			MapperRegistry.getInstance().get(entity.getClass()).update(entity);
		}
	}
	
	private void doDeletes() throws DatabaseActionException {
		for(BaseEntity entity : mRemoveEntities) {
			MapperRegistry.getInstance().get(entity.getClass()).delete(entity);
		}
	}
	
}