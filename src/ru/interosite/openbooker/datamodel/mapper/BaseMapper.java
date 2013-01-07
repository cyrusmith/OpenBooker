package ru.interosite.openbooker.datamodel.mapper;

import android.database.sqlite.SQLiteStatement;
import ru.interosite.openbooker.datamodel.domain.BaseEntity;
import ru.interosite.openbooker.datamodel.domain.IMapper;

public abstract class BaseMapper implements IMapper {
	
	protected DatabaseGateway mDatabaseGateway = null;
	
	public void setDatabaseGateway(DatabaseGateway gateway) {
		mDatabaseGateway = gateway;		
	}
	
	@Override
	public BaseEntity find(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int insert(BaseEntity entity) {
		SQLiteStatement statement = 
		return 0;
	}
	
	@Override
	public int update(BaseEntity entity) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int delete(BaseEntity entity) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	protected abstract String getTableName();
}