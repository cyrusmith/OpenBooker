package ru.interosite.openbooker.datamodel;

import java.util.ArrayList;
import java.util.List;

import ru.interosite.openbooker.datamodel.domain.BaseEntity;

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
	
	public static void execute() {
		CompoundAction action = mAction.get();
		action.doAction();
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
	
	private void doAction() {
		doInserts();
		doUpdates();
		doDeletes();
	}
	
	private void doInserts() {
		for(BaseEntity entity : mNewEntities) {
			//TODO
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