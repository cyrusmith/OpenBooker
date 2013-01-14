package ru.interosite.openbooker.datamodel;

import java.util.List;

import ru.interosite.openbooker.ApplicationConfig;
import ru.interosite.openbooker.datamodel.gateway.AccountGateway;
import ru.interosite.openbooker.datamodel.gateway.GatewayRegistry;
import ru.interosite.openbooker.datamodel.tables.TableModel;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DBAccess extends SQLiteOpenHelper {
	
	private final GatewayRegistry mGatewayRegistry;
	
	public DBAccess(Context context) {
		super(context, ApplicationConfig.DB_NAME, null, ApplicationConfig.getVersionCode(context));
		mGatewayRegistry = new GatewayRegistry(this);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE %s (%s)";
		List<TableModel> tableModels = TableModel.getModels();
		for(TableModel model : tableModels) {
			String tableName = model.getTableName();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	
	public GatewayRegistry getGatewayRegistry() {
		return mGatewayRegistry;
	}

}