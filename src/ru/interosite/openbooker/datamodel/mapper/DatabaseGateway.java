package ru.interosite.openbooker.datamodel.mapper;

import ru.interosite.openbooker.ApplicationInfo;
import ru.interosite.openbooker.datamodel.domain.CompoundAction;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DatabaseGateway implements ITransactionalDatabase {
	
	private static final String DATABASE_NAME = "open_booker_db";

	private static ThreadLocal<DatabaseGateway> mInstance = new ThreadLocal<DatabaseGateway>() {
		@Override
		protected DatabaseGateway initialValue() {
			return new DatabaseGateway();
		}
	};	
	
	public static DatabaseGateway getInstance() {
		return mInstance.get();
	}
	
	private SQLiteOpenHelper mDbOpener = null;	
	
	public DatabaseGateway init(Context context) {
		mDbOpener = new SQLiteOpenHelper(context, DATABASE_NAME, null, ApplicationInfo.getVersionCode(context)) {
						
			@Override
			public void onCreate(SQLiteDatabase db) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onUpgrade(SQLiteDatabase db, int oldVersion,
					int newVersion) {
				// TODO Auto-generated method stub
				
			}
			
		};
		return this;
	}
	
	public DatabaseGateway init(SQLiteOpenHelper customOpener) {
		//Useful for testing
		mDbOpener = customOpener;
		return this;
	}
	
	public SQLiteOpenHelper getDatabaseOpener() {
		return mDbOpener;
	}
	
	@Override
	public void transactionBegin() {
		if(mDbOpener!=null) {
			mDbOpener.getWritableDatabase().beginTransaction();
		}
	}
	
	@Override
	public void transactionEnd() {
		if(mDbOpener!=null) {
			mDbOpener.getWritableDatabase().endTransaction();
		}
	}
	
	@Override
	public void transactionSuccessful() {
		if(mDbOpener!=null) {
			mDbOpener.getWritableDatabase().setTransactionSuccessful();
		}
	}
}