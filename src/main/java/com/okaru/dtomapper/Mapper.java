package com.okaru.dtomapper;

import java.lang.reflect.Field;
import java.util.Map;

import com.okaru.dtomapper.annotations.MappedObject;
import com.okaru.dtomapper.annotations.Ignore;
import com.okaru.dtomapper.annotations.MappedField;
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
	 * Maps field values from <code>someDto</code> to fields of models in the
	 * <code>modelMap</code>.
	 * 
	 * @param someDto
	 * @param modelMap
	 */
	public static void toModel(Object someDto, Map<String, Object> modelMap){
		beginMapping(someDto, modelMap, true);
	}

	/**
	 * Maps field values from fields of the models in <code>modelMap</code> to 
	 * fields of <code>someDto</code>.
	 * 
	 * @param someDto
	 * @param modelMap
	 */
	public static void toDto(Object someDto, Map<String, Object> modelMap){
		beginMapping(someDto, modelMap, false);
	}
	
	private static void beginMapping(Object someDto, Map<String, Object> modelMap, boolean toModel){
		String clmd = getClassLevelMappingDestination(someDto);
		
		Field[] fields = someDto.getClass().getDeclaredFields();
		for(Field field : fields){
			if(!isIgnored(field)){
				String flmd = getFieldLevelMappingDestination(field);
	
				String destination = null;
				if(clmd == null && flmd == null){
					throw new MapperException("No model to map " +
							"field, " + field.getName() + ", to.");
				}else{
					destination = flmd == null? clmd:flmd;
				}
				transfer(field, someDto, destination, modelMap, toModel);
			}
		}
	}

	/**
	 * Returns the class level mapping destination if there is one.
	 * 
	 * @param someDto
	 * @return
	 */
	private static String getClassLevelMappingDestination(Object someDto) {
		MappedObject dest = someDto.getClass().getAnnotation(MappedObject.class);
		return (dest != null)? dest.key():null;
	}

	/**
	 * Returns the field-level mapping destination if there is one.
	 * 
	 * @param field
	 * @return
	 */
	private static String getFieldLevelMappingDestination(Field field) {
		MappedField mapping = field.getAnnotation(MappedField.class);
		if((mapping != null) && (!mapping.mappedObjectKey().isEmpty())){
			return mapping.mappedObjectKey();
		}
		return null;
	}

	/**
	 * Returns the name of the destination model's field that will be mapped
	 * to.
	 * 
	 * @param field
	 * @return
	 */
	private static String getDestinationFieldName(Field field) {
		MappedField mapping = field.getAnnotation(MappedField.class);
		if((mapping != null) && (!mapping.field().isEmpty())){
			return mapping.field();
		}
		return field.getName();
	}

	/**
	 * Maps <code>field</code> of <code>someDto</code> to the object mapped in
	 * <code>modelMap</code> with the key <code>destination</code>'s classname.
	 * 
	 * @param field
	 * @param someDto
	 * @param destination
	 * @param modelMap
	 */
	private static void transfer(Field field, Object someDto,
			String destination, Map<String, Object> modelMap, boolean toModel) {
		String fieldName = getDestinationFieldName(field);
			
		Object model = modelMap.get(destination);
		if(model != null){
			Field modelField = null;
			try {
				modelField = model.getClass().getDeclaredField(fieldName);
				if(modelField.getType().equals(field.getType())){
					boolean modelFieldAccessible = modelField.isAccessible();
					boolean dtoFieldAccessible = field.isAccessible();
					
					modelField.setAccessible(true);
					field.setAccessible(true);
					
					if(toModel){
						modelField.set(model, field.get(someDto));
					}else{
						field.set(someDto, modelField.get(model));
					}
					
					modelField.setAccessible(modelFieldAccessible);
					field.setAccessible(dtoFieldAccessible);
				}else{
					throw new MapperException("Cannot map " + 
							field.getName() + " of " + 
							someDto.getClass().getName() + " to " + 
							modelField.getName() + " of " + 
							model.getClass().getName() + ".  The types" +
							" need to be the same.");
				}
			} catch (NoSuchFieldException e) {
				throw new MapperException("Field, " + fieldName + ", does " +
						"not exist on model " + model.getClass().getName(), e);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}else{
			throw new MapperException("No model with the key \"" 
					+ destination + "\" found in modelMap. Referenced by " + 
					someDto.getClass().getName() + "." + field.getName());
		}
	}

	/**
	 * Returns whether the specified field is transient or not.
	 * 
	 * @param f
	 * @return
	 */
	private static boolean isIgnored(Field f){
		return (f.getAnnotation(Ignore.class) != null);
	}
}
