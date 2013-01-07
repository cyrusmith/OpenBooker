package ru.interosite.openbooker.datamodel.domain;

import java.util.ArrayList;
import java.util.List;

import ru.interosite.openbooker.datamodel.mapper.MapperRegistry;


public class CompoundAction {
	
	public interface ICompoundActionListener {
		public void onBeginTransaction();
		public void onTransactionSuccessful();
		public void onEndTransaction();
	}
	
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
		CompoundAction action = mAction.get();
		action.executeTransaction();
		return true;
	}
		
	private List<BaseEntity> mNewEntities = new ArrayList<BaseEntity>();
	private List<BaseEntity> mDirtyEntities = new ArrayList<BaseEntity>();
	private List<BaseEntity> mRemoveEntities = new ArrayList<BaseEntity>();
	
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
	
	private void executeTransaction() {
		try {
			doInserts();
			doUpdates();
			doDeletes();
		}
	}
	
	private void doInserts() {
		for(BaseEntity entity : mNewEntities) {
			MapperRegistry.getInstance().get(entity.getClass()).insert(entity);
		}
	}
	
	private void doUpdates() {
		for(BaseEntity entity : mDirtyEntities) {
			//TODO
		}
	}
	
	private void doDeletes() {
		for(BaseEntity entity : mRemoveEntities) {
			//TODO
		}
	}
}