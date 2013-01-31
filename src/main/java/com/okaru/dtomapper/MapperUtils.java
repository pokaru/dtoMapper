package com.okaru.dtomapper;

import java.lang.reflect.Field;

import com.okaru.dtomapper.annotation.Embedded;
import com.okaru.dtomapper.annotation.Ignore;
import com.okaru.dtomapper.annotation.MappedField;
import com.okaru.dtomapper.annotation.MappedObject;
import com.okaru.dtomapper.annotation.MappedField.MapsTo;

/**
 * Utlitiy methods to assist the Mapper class.
 * 
 * @author pokaru
 *
 */
public class MapperUtils {

	/**
	 * Returns the class level mapping destination if there is one.
	 * 
	 * @param someDto
	 * @return
	 */
	public static String getClassLevelMappingDestination(Object someDto) {
		MappedObject dest = someDto.getClass().getAnnotation(MappedObject.class);
		return (dest != null)? dest.key():null;
	}

	/**
	 * Returns the field-level mapping destination if there is one.
	 * 
	 * @param field
	 * @return
	 */
	public static String getFieldLevelMappingDestination(Field field) {
		MappedField mapping = field.getAnnotation(MappedField.class);
		if((mapping != null) && (!mapping.mappedObjectKey().isEmpty())){
			return mapping.mappedObjectKey();
		}
		return null;
	}

	/**
	 * Returns the name of the destination object's field that will be mapped
	 * to.
	 * 
	 * @param field
	 * @return
	 */
	public static String getDestinationFieldName(Field field) {
		MappedField mapping = field.getAnnotation(MappedField.class);
		if((mapping != null) && (!mapping.field().isEmpty())){
			return mapping.field();
		}
		return field.getName();
	}
	
	/**
	 * Returns true if this field is supposed to map to an object field; false
	 * otherwise.
	 * 
	 * @param field
	 * @return
	 */
	public static boolean mapsToField(Field field){
		MappedField mapping = field.getAnnotation(MappedField.class);
		if((mapping != null) && (mapping.mapsTo().equals(MapsTo.SETTER))){
			return false;
		}
		return true;
	}

	/**
	 * Returns whether the specified field is marked ignored or not.
	 * 
	 * @param f
	 * @return
	 */
	public static boolean isIgnored(Field f){
		return (f.getAnnotation(Ignore.class) != null);
	}
	
	/**
	 * Returns whether the specified field is an embedded dto object or not.
	 * 
	 * @param f
	 * @return
	 */
	public static boolean isEmbeddedDto(Field f){
		return (f.getAnnotation(Embedded.class) != null);
	}
	
	/**
	 * A utility method for constructing a setter method from a field name.
	 * 
	 * @param f
	 * @return
	 */
	public static String getSetterMethodName(String fieldName){
		return "set" + new String(fieldName.charAt(0) + "").toUpperCase().
				concat(fieldName.substring(1));
	}
	
	/**
	 * A utility method for constructing a getter method from a field name.
	 * 
	 * @param f
	 * @return
	 */
	public static String getGetterMethodName(String fieldName){
		return "get" + new String(fieldName.charAt(0) + "").toUpperCase().
				concat(fieldName.substring(1));
	}
}
