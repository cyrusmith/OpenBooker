package ru.interosite.openbooker.datamodel.tables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TableModel {
	
	public static final String ID = "id";	
	
	private static final Map<Class<? extends TableModel>, TableModel> mModels = new HashMap<Class<? extends TableModel>, TableModel>();
	
	static {
		mModels.put(AccountBalanceTableModel.class, new AccountBalanceTableModel());
		mModels.put(AccountsTableModel.class, new AccountsTableModel());
		mModels.put(AccountTypeTableModel.class, new AccountTypeTableModel());
		mModels.put(OperationTableModel.class, new OperationTableModel());
		mModels.put(OperationFactTableModel.class, new OperationFactTableModel());
		mModels.put(IncomeSourceTableModel.class, new IncomeSourceTableModel());
		mModels.put(ExpenseTypeTableModel.class, new ExpenseTypeTableModel());
	}
	
	public static TableModel getModel(Class<? extends TableModel> modelClass) {
		TableModel model = mModels.get(modelClass);
		if(model==null) {
			throw new IllegalArgumentException("No model info for " + modelClass + " is found");	
		}
		return model;
	}
	
	public static List<TableModel> getModels() {
		return Collections.unmodifiableList(new ArrayList<TableModel>(mModels.values()));
	}
	
	private String mTableName = null;		
	private String mCompoundKeyString = null;
	private Map<String, Column> mColumns = new LinkedHashMap<String, Column>();
	
	protected List<String> mInsertsOnCreate = new ArrayList<String>();		
	
	TableModel() {
		addColumn(ID, "INTEGER PRIMARY KEY AUTOINCREMENT");
	}
	
	protected final void setTableName(String name) {
		mTableName = name;
	}
	
	protected final void addColumn(String name, String typeDef) {
		if(!mColumns.containsKey(name)) {
			mColumns.put(name, new Column(name, typeDef));
		}
	}
	
	protected final void removeColumn(String name) {
		mColumns.remove(name);
	}
	
	protected final void setCompoundKey(String...cols) {
		
		if(cols.length == 0) {
			return;
		}
		
		removeColumn(ID);
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
	
	public final List<Column> getColumns() {
		return new ArrayList<Column>(mColumns.values());
	}
	
	public final String[] getColumnNames() {
		List<Column> cols = getColumns();
		String[] colsWithId = new String[cols.size()+1];
		colsWithId[0] = TableModel.ID;
		for(int i=1; i<colsWithId.length; i++) {
			colsWithId[i] = cols.get(i-1).getName();
		}		
		return colsWithId;
	}
	
	public final String getTypeFor(String colName) {
		Column col = mColumns.get(colName);
		if(col==null) {
			throw new IllegalArgumentException("Not type info for " + colName + " found");
		}
		return col.getType();
	}
	
	public final List<String> getInsertsOnCreate() {
		return mInsertsOnCreate;
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