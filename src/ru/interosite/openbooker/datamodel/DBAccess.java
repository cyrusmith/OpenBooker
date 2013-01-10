package ru.interosite.openbooker.datamodel;

import ru.interosite.openbooker.ApplicationConfig;
import ru.interosite.openbooker.datamodel.gateway.GatewayRegistry;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DBAccess extends SQLiteOpenHelper {
	
	public static final String ACCOUNTS_TABLE_NAME = "accounts";
	
	private final GatewayRegistry mGatewayRegistry;
	
	public DBAccess(Context context) {
		super(context, ApplicationConfig.DB_NAME, null, ApplicationConfig.getVersionCode(context));
		mGatewayRegistry = new GatewayRegistry(this);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String createAccountsSql = "CREATE TABLE "
				+ ACCOUNTS_TABLE_NAME + " (" + 
				BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				"title REAL," + 
				"type_id INTEGER);";
		db.execSQL(createAccountsSql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	
	public GatewayRegistry getGatewayRegistry() {
		return mGatewayRegistry;
	}

}
