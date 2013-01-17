package ru.interosite.openbooker.datamodel.gateway;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.interosite.openbooker.datamodel.DBAccess;
import ru.interosite.openbooker.datamodel.domain.BaseEntity;
import ru.interosite.openbooker.datamodel.tables.TableModel;
import ru.interosite.openbooker.datamodel.tables.TableModel.Column;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public abstract class DatabaseGateway {
	
	private static final Logger LOGGER = LoggerFactory.getLogger("ru.interosite.openbooker.datamodel.gateway.DatabaseGateway");
	
	protected DBAccess mDba = null;
	
	public DatabaseGateway(DBAccess dba) {
		mDba = dba;
	}
	
	public Cursor findAll(String orderCol, String orderDir) {
		String orderBy = null;
		if(orderCol!=null) {
			orderBy = orderCol;
			if(orderDir!=null) {
				orderBy+=" " + orderDir;
			}
		}
		SQLiteDatabase db = mDba.getReadableDatabase();
		return db.query(getTableName(), getColumns(), null, null, null, null, orderBy);		
	}
		
	public BaseEntity findById(long id) {
		SQLiteDatabase db = mDba.getReadableDatabase();
		Cursor c = db.query(getTableName(), getColumns(), TableModel.ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
		if(c!=null) {
			try {
				if(c.getCount()==1) {
					c.moveToFirst();
				}
				else {
					return BaseEntity.UNKNOWN_ENTITY;
				}			
				BaseEntity entity = loadEntity(id, c);
				if(entity!=null) {
					return entity;
				}
			}
			catch(Exception e) {
				LOGGER.error("Error when loading entity: {}", e.getMessage());
			}
			finally {
				c.close();
			}
		}
		return BaseEntity.UNKNOWN_ENTITY;
	}
	
	public long insert(BaseEntity entity) {
		if(entity.getId()!=null) {
			throw new IllegalStateException("Entity has id. Cannot insert.");	
		}
		SQLiteDatabase db = mDba.getWritableDatabase();
		long newId = db.insert(getTableName(), null, getContentValues(entity));
		if(newId > 0) {
			entity.setId(newId);
		}
		return newId;
	}
	
	public int update(BaseEntity entity) {
		if(entity.getId()==null) {
			throw new IllegalStateException("Entity has no id. Cannot update.");
		}
		SQLiteDatabase db = mDba.getWritableDatabase();
		return db.update(getTableName(), getContentValues(entity), getIdentityWhereClause(entity), getIdentityWhereArgs(entity));
	}
	
	public int delete(BaseEntity entity) {
		if(entity.getId()==null) {
			throw new IllegalStateException("Entity has no id. Cannot delete.");
		}		
		SQLiteDatabase db = mDba.getWritableDatabase();
		return db.delete(getTableName(), getIdentityWhereClause(entity), getIdentityWhereArgs(entity));
	}

	protected final String getTableName() {
		return getTableModel().getTableName();
	}
	
	protected final String[] getColumns() {
		List<Column> cols = getTableModel().getColumns();
		String[] colNames = new String[cols.size()];
		for(int i=0; i < colNames.length; i++) {
			colNames[i] = cols.get(i).getName();
		}
		return colNames;
	}	
	
	protected String[] getIdentityWhereArgs(BaseEntity entity) {
		return new String[]{ String.valueOf(entity.getId()) };
	}
	
	protected String getIdentityWhereClause(BaseEntity entity) {
		return TableModel.ID + "=?";
	}
	
	private ContentValues getContentValues(BaseEntity entity) {
		ContentValues values = doGetContentValues(entity);
		if(values==null) {
			return new ContentValues();
		}
		return values;
	}
	
	protected abstract TableModel getTableModel();	
	protected abstract ContentValues doGetContentValues(BaseEntity entity) ;	
	protected abstract BaseEntity loadEntity(long id, Cursor c);
	
}