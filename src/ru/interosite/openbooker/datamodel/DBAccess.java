package ru.interosite.openbooker.datamodel;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.interosite.openbooker.ApplicationConfig;
import ru.interosite.openbooker.datamodel.gateway.AccountGateway;
import ru.interosite.openbooker.datamodel.gateway.GatewayRegistry;
import ru.interosite.openbooker.datamodel.tables.TableModel;
import ru.interosite.openbooker.datamodel.tables.TableModel.Column;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DBAccess extends SQLiteOpenHelper {
	
	private static final Logger LOGGER = LoggerFactory.getLogger("ru.interosite.openbooker.datamodel.DBAccess");
	
	public DBAccess(Context context) {
		super(context, ApplicationConfig.DB_NAME, null, ApplicationConfig.getInstance().getVersionCode());
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE %s (%s);";
		List<TableModel> tableModels = TableModel.getModels();
		for(TableModel model : tableModels) {
			String tableName = model.getTableName();
			List<Column> cols = model.getColumns();
			if(cols.size() > 0) {
				StringBuilder sb = new StringBuilder();
				Column firstCol = cols.remove(0);
				sb.append(firstCol.getName()).append(" ").append(firstCol.getType());
				for(Column col: cols) {
					sb.append(", ").append(col.getName()).append(" ").append(col.getType());
				}
				
				if(model.isCompoundKey()) {
					sb.append(", ").append(model.getCompoundKeyString());	
				}
				
				String createSql = String.format(sql, tableName, sb.toString());
				LOGGER.debug("Execute create query: {}", createSql);
				db.execSQL(createSql);
			}			
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	
}