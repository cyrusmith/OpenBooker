package ru.interosite.openbooker.datamodel.gateway;

import ru.interosite.openbooker.datamodel.DBAccess;
import ru.interosite.openbooker.datamodel.domain.BaseEntity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public abstract class DatabaseGateway {
	
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
		Cursor c = db.query(getTableName(), getColumns(), BaseColumns._ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
		if(c!=null) {
			try {
				if(c.getCount()==1) {
					c.moveToFirst();
				}
				else {
					throw new IllegalStateException("Cursor expected count is 1 but found " + c.getCount());
				}			
				return loadEntity(id, c);
			}
			finally {
				c.close();
			}
		}
		return null;
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

	protected abstract ContentValues getContentValues(BaseEntity entity) ;	
	protected abstract String getTableName();
	protected abstract String[] getColumns();
	protected abstract BaseEntity loadEntity(long id, Cursor c);
	
}