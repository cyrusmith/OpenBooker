package ru.interosite.openbooker.datamodel.mapper;

import java.util.HashMap;
import java.util.Map;

import ru.interosite.openbooker.datamodel.domain.BaseEntity;
import ru.interosite.openbooker.datamodel.domain.IMapper;

public class MapperRegistry {
	
	private static final String MAPPER_PACKAGE_NAME = MapperRegistry.class.getPackage().getName();
	
	private static final Map<Class<? extends BaseEntity>, IMapper> mMappers = new HashMap<Class<? extends BaseEntity>, IMapper>();
			
	public static IMapper get(Class<? extends BaseEntity> clazz) {
		IMapper mapper = null;
		if(mMappers.containsKey(clazz)) {
			mapper = mMappers.get(clazz); 
		}		
		else {
			String class2find = MAPPER_PACKAGE_NAME + "." + clazz.getSimpleName() + "Mapper";
			try {			
				Class<?> mapperClass = Class.forName(class2find);
				if(!IMapper.class.isAssignableFrom(mapperClass)) {
					throw new InvalidMapperClass("Mapper class is not a IMapper");	
				}
				mapper = (IMapper)mapperClass.newInstance();
				mMappers.put(clazz, mapper);
			} catch (ClassNotFoundException e) {
				throw new InvalidMapperClass("Cannot find class " + class2find);
			}
			catch(ClassCastException e) {
				throw new InvalidMapperClass("Cannot cast class "  + class2find);
			} catch (InstantiationException e) {
				throw new InvalidMapperClass("Cannot instantiate " + class2find);				
			} catch (IllegalAccessException e) {
				throw new InvalidMapperClass("Cannot access " + class2find);				
			}
			
		}
		return mapper;
	}
}