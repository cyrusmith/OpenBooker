package ru.interosite.openbooker.datamodel.domain;

public interface IMapper {
	public BaseEntity find(Long id);
	
	public int insert(BaseEntity entity);
	public int update(BaseEntity entity);
	public int delete(BaseEntity entity);
}