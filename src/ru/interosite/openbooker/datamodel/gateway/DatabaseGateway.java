package ru.interosite.openbooker.datamodel.gateway;

import java.util.Map;

import ru.interosite.openbooker.datamodel.DBAccess;
import ru.interosite.openbooker.datamodel.domain.BaseEntity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
		//TODO
		db.quer
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
	
}