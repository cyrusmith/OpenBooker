package ru.interosite.openbooker.datamodel.tables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableModel {
	
	public static final String ID = "id";	
	
	private static final Map<Class<? extends TableModel>, TableModel> mModels = new HashMap<Class<? extends TableModel>, TableModel>();
	
	static {
		mModels.put(AccountBalanceTableModel.class, new AccountBalanceTableModel());
		mModels.put(AccountsTableModel.class, new AccountsTableModel());
		mModels.put(AccountTypeTableModel.class, new AccountTypeTableModel());
		mModels.put(Operation.class, new Operation());
		mModels.put(OperationArguments.class, new OperationArguments());
		mModels.put(OperationFactTableModel.class, new OperationFactTableModel());
		mModels.put(OperationTypes.class, new OperationTypes());
	}
	
	public static TableModel getModel(Class<? extends TableModel> modelClass) {
		TableModel model = mModels.get(modelClass);
		if(model==null) {
			throw new IllegalArgumentException("Not model info for " + modelClass + " found");	
		}
		return model;
	}
	
	public static List<TableModel> getModels() {
		return Collections.unmodifiableList(new ArrayList<TableModel>(mModels.values()));
	}
	
	private String mCompoundKeyString = null;
	
	protected String mTableName = null;	
	protected List<String> mColumns = new ArrayList<String>();
	protected Map<String, String> mNameTypeMap = new HashMap<String, String>();	
	protected List<String> mInsertsOnCreate = new ArrayList<String>();		
	
	TableModel() {
		mNameTypeMap.put(ID, "INTEGER PRIMARY KEY AUTOINCREMENT");
	}
	
	protected final void setCompoundKey(String...cols) {
		if(cols.length == 0) {
			return;
		}
		
		mNameTypeMap.remove(ID);
		List<String> colsList = new ArrayList<String>(Arrays.asList(cols));
		StringBuilder sb = new StringBuilder();
		sb.append("PRIMARY KEY (");
		sb.append(colsList.remove(0));
		for(String col : colsList) {
			sb.append(", ");	
			sb.append(col);	
		}
		sb.append(")");
		mCompoundKeyString = sb.toString(); 
	}
	
	public String getCompoundKeyString() {
		return mCompoundKeyString;
	}
	
	public boolean isCompoundKey() {
		return mCompoundKeyString!=null;
	}
	
	public final String getTableName() {
		return mTableName;
	}
	
	public final List<String> getColumns() {
		return Collections.unmodifiableList(mColumns);
	}
	
	public final String getTypeFor(String colName) {
		String typeInfo = mNameTypeMap.get(colName);
		if(typeInfo==null) {
			throw new IllegalArgumentException("Not type info for " + colName + " found");
		}
		return typeInfo;
	}
	
	public final List<String> getInsertsOnCreate() {
		return mInsertsOnCreate;
	}
	
	public final String getCreateColumnsString() {
		
	}
	
	public static final class Column {
		
		private final String mName;
		private final String mType;
		
		Column(String name, String type) {
			if(name==null) {
				throw new IllegalArgumentException("Name of column is null");
			}
			if(type==null) {
				throw new IllegalArgumentException("Type of column is null");
			}
			mName = name;
			mType = type;
		}
		
		public String getName() {
			return mName;
		}
		
		public String getType() {
			return mType;
		}
		
		@Override
		public boolean equals(Object obj) {
			if(!(obj instanceof Column)) {
				return false;
			}
			Column other = (Column)obj;			
			return mName.equals(other.mName);
		}
		
		@Override
		public int hashCode() {
			return mName.hashCode();
		}
		
	}
	
}