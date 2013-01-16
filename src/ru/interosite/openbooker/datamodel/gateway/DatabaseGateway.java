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
	
	public static final DatabaseGateway UNKNOWN_GATEWAY = new UnknownGateway(null);	
	
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
		SQLiteDatabase db = mDba.getWritableDatabase();
		long newId = db.insert(getTableName(), null, getContentValues(entity));
		return newId;
	}
	
	public int update(BaseEntity entity) {
		return 0;
	}
	
	public int delete(BaseEntity entity) {
		return 0;
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