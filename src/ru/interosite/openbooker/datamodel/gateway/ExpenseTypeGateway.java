package ru.interosite.openbooker.datamodel.gateway;

import java.util.List;

import org.slf4j.LoggerFactory;

import ru.interosite.openbooker.datamodel.DomainRequestContext;
import ru.interosite.openbooker.datamodel.domain.Account;
import ru.interosite.openbooker.datamodel.domain.AccountType;
import ru.interosite.openbooker.datamodel.domain.BaseEntity;
import ru.interosite.openbooker.datamodel.domain.ExpenseType;
import ru.interosite.openbooker.datamodel.domain.Funds;
import ru.interosite.openbooker.datamodel.domain.UnknownEntity;
import ru.interosite.openbooker.datamodel.tables.AccountsTableModel;
import ru.interosite.openbooker.datamodel.tables.ExpenseTypeTableModel;
import ru.interosite.openbooker.datamodel.tables.TableModel;
import android.content.ContentValues;
import android.database.Cursor;

public class ExpenseTypeGateway extends DatabaseGateway {

	private static final String TAG = "ru.interosite.openbooker.datamodel.gateway.ExpenseTypeGateway";  
	
	@Override
	protected Class<? extends BaseEntity> getEntityClass() {
		return ExpenseType.class;
	}
	
	@Override
	protected ContentValues doGetContentValues(BaseEntity entity) {
		ExpenseType type = (ExpenseType)entity;
		ContentValues values = new ContentValues();
		values.put(ExpenseTypeTableModel.TITLE, type.getTitle());
		
		long parentId = 0;
		if(type.getParent()!=ExpenseType.ROOT) {
			parentId = type.getParent().getId();
		}
		values.put(ExpenseTypeTableModel.PARENT_TYPE_ID, parentId);
		return values;
	}

	@Override
	protected BaseEntity loadEntity(long id, Cursor c) {
		String title = c.getString(c.getColumnIndex(ExpenseTypeTableModel.TITLE));
		int parentId = c.getInt(c.getColumnIndex(ExpenseTypeTableModel.PARENT_TYPE_ID));

		ExpenseType parent = ExpenseType.ROOT;
		
		if(parentId > 0) {
			BaseEntity parentEntity = DomainRequestContext.getInstance().getGatewayRegistry().get(ExpenseType.class).findById(parentId);
			if(parentEntity instanceof ExpenseType) {
				parent = (ExpenseType)parentEntity;
			}
			else {
				throw new IllegalStateException("Cannot load parent type with id " + parentId);
			}
		}
		
		ExpenseType expType = mEntitiesFactory.createExpenseType(title, parent);
		expType.setId(id);
		
		return expType;
	}

	@Override
	protected TableModel getTableModel() {
		return TableModel.getModel(ExpenseTypeTableModel.class);
	}	
	
}