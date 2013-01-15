package ru.interosite.openbooker.datamodel.gateway;

import java.util.List;

import ru.interosite.openbooker.datamodel.DBAccess;
import ru.interosite.openbooker.datamodel.domain.Account;
import ru.interosite.openbooker.datamodel.domain.AccountType;
import ru.interosite.openbooker.datamodel.domain.BaseEntity;
import ru.interosite.openbooker.datamodel.domain.EntitiesFactory;
import ru.interosite.openbooker.datamodel.domain.Funds;
import ru.interosite.openbooker.datamodel.domain.IncomeSource;
import ru.interosite.openbooker.datamodel.tables.AccountsTableModel;
import ru.interosite.openbooker.datamodel.tables.IncomeSourceTableModel;
import ru.interosite.openbooker.datamodel.tables.TableModel;
import android.content.ContentValues;
import android.database.Cursor;

public class IncomeSourceGateway extends DatabaseGateway {

	public IncomeSourceGateway(DBAccess dba) {
		super(dba);
	}

	@Override
	protected ContentValues getContentValues(BaseEntity entity) {
		
		if(!(entity instanceof IncomeSource)) {
			throw new IllegalArgumentException();
		}
		
		IncomeSource source = (IncomeSource)entity;
		
		ContentValues values = new ContentValues();
		values.put(IncomeSourceTableModel.TITLE, source.getTitle());
		
		return values;
	}
	
	@Override
	protected BaseEntity loadEntity(long id, Cursor c) {
		String title = c.getString(c.getColumnIndex(IncomeSourceTableModel.TITLE));
		EntitiesFactory.createIncomeSource(title);
		return acc;
	}	
	@Override
	protected TableModel getTableModel() {
		return TableModel.getModel(IncomeSourceTableModel.class);
	}		
}
