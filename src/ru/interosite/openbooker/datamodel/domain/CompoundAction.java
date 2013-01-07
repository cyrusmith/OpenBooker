package ru.interosite.openbooker.datamodel.domain;

import java.util.ArrayList;
import java.util.List;

import ru.interosite.openbooker.datamodel.mapper.DatabaseActionException;
import ru.interosite.openbooker.datamodel.mapper.MapperRegistry;


public class CompoundAction {
	
	public static interface ICompoundActionListener {
		public void onBegin();
		public void onSuccessful();
		public void onEnd();
	}
	
	private static ThreadLocal<CompoundAction> mAction = new ThreadLocal<CompoundAction>() {
		protected CompoundAction initialValue() {
			return new CompoundAction();
		};
	};
	
	public static void open() {
		mAction.set(new CompoundAction());
	}
	
	public static void open(ICompoundActionListener listener) {
		if(listener==null) {
			throw new IllegalArgumentException("ICompoundActionListener is null");
		}
		mAction.set(new CompoundAction(listener));
	}
	
	public static CompoundAction getCurrentAction() {
		return mAction.get();
	}
	
	public static boolean execute() {
		CompoundAction action = mAction.get();
		action.executeTransaction();
		return true;
	}
		
	private ICompoundActionListener mTransactionListener = null;
	
	private List<BaseEntity> mNewEntities = new ArrayList<BaseEntity>();
	private List<BaseEntity> mDirtyEntities = new ArrayList<BaseEntity>();
	private List<BaseEntity> mRemoveEntities = new ArrayList<BaseEntity>();
	
	private CompoundAction() {
		this(null);
	}
	
	private CompoundAction(ICompoundActionListener listener) {
		mTransactionListener = listener;
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
	
	private void executeTransaction() {
		try {
			fireTransactionBegin();
			doInserts();
			doUpdates();
			doDeletes();
			fireTransactionSuccessful();
		}
		catch(DatabaseActionException e) {
			
		}
		catch(Exception e) {
			
		}
		finally {
			fireTransactionEnd();
		}		
	}
	
	private void doInserts() throws DatabaseActionException {
		for(BaseEntity entity : mNewEntities) {
			MapperRegistry.getInstance().get(entity.getClass()).insert(entity);
		}
	}
	
	private void doUpdates() throws DatabaseActionException {
		for(BaseEntity entity : mDirtyEntities) {
			//TODO
		}
	}
	
	private void doDeletes() throws DatabaseActionException {
		for(BaseEntity entity : mRemoveEntities) {
			//TODO
		}
	}
	
	private void fireTransactionBegin() {
		if(mTransactionListener!=null) {
			mTransactionListener.onBegin();
		}
	}
	
	private void fireTransactionSuccessful() {
		if(mTransactionListener!=null) {
			mTransactionListener.onSuccessful();
		}
	}
	
	private void fireTransactionEnd() {
		if(mTransactionListener!=null) {
			mTransactionListener.onEnd();
		}
	}
}