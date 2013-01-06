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
	
	private void doAction() {
		
	}
	
	public void addNew(BaseEntity entity) {
		
		if(entity==null) {
			return;
		}
		
		if(mNewEntities.contains(entity)) {
			return;
		}
		
		if(mRemoveEntities.contains(entity)) {
			return;
		}
		
		if(mDirtyEntities.contains(entity)) {
			return;
		}
		
		mNewEntities.add(entity);
	}
	
}