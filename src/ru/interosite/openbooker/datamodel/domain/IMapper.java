package ru.interosite.openbooker.datamodel.domain;

import ru.interosite.openbooker.datamodel.gateway.DatabaseActionException;

public interface IMapper {
	public BaseEntity find(Long id);
	
	public int insert(BaseEntity entity) throws DatabaseActionException;
	public int update(BaseEntity entity) throws DatabaseActionException;
	public int delete(BaseEntity entity) throws DatabaseActionException;
	
}