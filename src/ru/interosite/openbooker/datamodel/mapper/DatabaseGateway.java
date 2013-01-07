package ru.interosite.openbooker.datamodel.mapper;

import ru.interosite.openbooker.ApplicationInfo;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseGateway {
	
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
	
	public void init(Context context) {
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
	}
	
	public SQLiteOpenHelper getDatabaseOpener() {
		return mDbOpener;
	}
	
}