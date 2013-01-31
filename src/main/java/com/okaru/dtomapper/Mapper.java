package com.okaru.dtomapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import com.okaru.dtomapper.exception.MapperException;

/**
 * Maps values from dto objects to other objects and vice versa.  Mappings are
 * specified within the MappedField and MappedObject annotations within the
 * dto.
 * 
 * @author pokaru
 */
public class Mapper{
	/**
	 * Maps field values from <code>someDto</code> to fields of objects in the
	 * <code>objectMap</code>.
	 * 
	 * @param someDto
	 * @param objectMap
	 */
	public static void fromDto(Object someDto, Map<String, Object> objectMap) throws MapperException{
		beginMapping(someDto, objectMap, true);
	}

	/**
	 * Maps field values from fields of the objects in <code>objectMap</code> to 
	 * fields of <code>someDto</code>.
	 * 
	 * @param someDto
	 * @param objectMap
	 */
	public static void toDto(Object someDto, Map<String, Object> objectMap) throws MapperException{
		beginMapping(someDto, objectMap, false);
	}
	
	private static void beginMapping(Object someDto, Map<String, Object> objectMap, boolean toObject) throws MapperException{
		if(someDto == null){
			throw new MapperException("The DTO cannot be null.");
		}
		
		if(objectMap == null){
			throw new MapperException("The object map cannot be null.");
		}
		
		String clmd = MapperUtils.getClassLevelMappingDestination(someDto);
		
		Field[] fields = someDto.getClass().getDeclaredFields();
		for(Field field : fields){
			if(!MapperUtils.isIgnored(field)){
				if(MapperUtils.isEmbeddedDto(field)){
					Object embeddedDto;
					try {
						field.setAccessible(true);
						embeddedDto = field.get(someDto);
						if(embeddedDto != null){
							beginMapping(embeddedDto, objectMap, toObject);
						}else{
							if(!toObject){
								throw new MapperException("Embedded DTO \"" + 
										field.getName() + "\" of " + 
										someDto.getClass().getName() + " is null. " +
											"Embedded DTOs cannot be null when " +
											"being mapped to.");
							}
						}
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}else{
					String flmd = MapperUtils.getFieldLevelMappingDestination(field);
		
					String destination = null;
					if(clmd == null && flmd == null){
						throw new MapperException("No object to map " +
								"field, " + field.getName() + ", to.  Check to" +
							    " make sure you reference an object in either " +
							    "@MappedObject on the DTO class or @MappedField" +
							    " on the \""+field.getName()+"\" within " + 
							    someDto.getClass().getName());
					}
					destination = (flmd == null)? clmd:flmd;
					transfer(field, someDto, destination, objectMap, toObject);
				}
			}
		}
	}

	/**
	 * Maps <code>field</code> of <code>someDto</code> to the object mapped in
	 * <code>objectMap</code> with the key <code>destination</code>'s classname.
	 * 
	 * @param field
	 * @param someDto
	 * @param destination
	 * @param objectMap
	 */
	private static void transfer(Field field, Object someDto,
			String destination, Map<String, Object> objectMap, boolean toObject) throws MapperException{
		String fieldName = MapperUtils.getDestinationFieldName(field);
		Object object = objectMap.get(destination);
		if(object != null){
			if(MapperUtils.mapsToField(field)){
				handleFieldMapping(field, someDto, fieldName, object, toObject);
			}else{
				handleSetterMapping(field, someDto, fieldName, object, toObject);
			}
		}else{
			throw new MapperException("No object with the key \"" 
					+ destination + "\" found in objectMap. Referenced by " + 
					someDto.getClass().getName() + "." + field.getName());
		}
	}
	
	private static void handleFieldMapping(Field field, Object someDto,
			String fieldName, Object object, boolean toObject) throws MapperException{
		Field objectField = null;
		try {
			objectField = object.getClass().getDeclaredField(fieldName);
			if(objectField.getType().equals(field.getType())){
				objectField.setAccessible(true);
				field.setAccessible(true);
				
				if(toObject){
					objectField.set(object, field.get(someDto));
				}else{
					field.set(someDto, objectField.get(object));
				}
			}else{
				throw new MapperException("Cannot map " + 
						field.getName() + " of " + 
						someDto.getClass().getName() + " to " + 
						objectField.getName() + " of " + 
						object.getClass().getName() + ".  The types" +
						" need to be the same.");
			}
		} catch (NoSuchFieldException e) {
			throw new MapperException("Field, " + fieldName + ", does " +
					"not exist on object " + object.getClass().getName(), e);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	private static void handleSetterMapping(Field field, Object someDto,
			String fieldName, Object object, boolean toObject) throws MapperException{
		try {
			if(toObject){
				String setterMethodName = MapperUtils.getSetterMethodName(fieldName);
				field.setAccessible(true);
				Method method;
				try {
					method = object.getClass().getDeclaredMethod(setterMethodName, field.get(someDto).getClass());
					method.setAccessible(true);
					Object someObject = field.get(someDto);
					method.invoke(object, someObject);
				} catch (NoSuchMethodException e) {
					throw new MapperException("No such method \""+setterMethodName+
							"("+field.get(someDto).getClass().getName()+")\" " +
									"in object " + object.getClass().getName(), e);
				} catch (IllegalArgumentException e) {
					throw new MapperException(e);
				}
			}else{
				String getterMethodName = MapperUtils.getGetterMethodName(fieldName);
				Method method;
				try {
					method = object.getClass().getDeclaredMethod(getterMethodName);
					method.setAccessible(true);
					field.setAccessible(true);
					field.set(someDto, method.invoke(object));
				} catch (NoSuchMethodException e) {
					throw new MapperException("No such method \""+getterMethodName+"()\" " +
									"in object " + object.getClass().getName(), e);
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

}