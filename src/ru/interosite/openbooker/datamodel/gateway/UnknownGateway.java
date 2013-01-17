package ru.interosite.openbooker.datamodel.gateway;

import ru.interosite.openbooker.datamodel.DBAccess;
import ru.interosite.openbooker.datamodel.domain.BaseEntity;
import ru.interosite.openbooker.datamodel.tables.TableModel;
import android.content.ContentValues;
import android.database.Cursor;

public class UnknownGateway extends DatabaseGateway {

	private static final ThreadLocal<UnknownGateway> mInstance = new ThreadLocal<UnknownGateway>() {
		protected UnknownGateway initialValue() {
			return new UnknownGateway(null);
		};
	};
	
	public static UnknownGateway getInstance() {
		return mInstance.get();
	}	
	
	private UnknownGateway(DBAccess dba) {
		super(dba);
	}

	@Override
	protected ContentValues doGetContentValues(BaseEntity entity) {
		return null;
	}
	
	@Override
	protected BaseEntity loadEntity(long id, Cursor c) {
		return null;
	}

	@Override
	protected TableModel getTableModel() {
		return null;
	}	
	
}