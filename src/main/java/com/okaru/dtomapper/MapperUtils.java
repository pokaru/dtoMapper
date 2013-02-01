package com.okaru.dtomapper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.okaru.dtomapper.annotation.Convert;
import com.okaru.dtomapper.annotation.Embedded;
import com.okaru.dtomapper.annotation.Ignore;
import com.okaru.dtomapper.annotation.MappedField;
import com.okaru.dtomapper.annotation.MappedField.MapsTo;
import com.okaru.dtomapper.annotation.MappedObject;
import com.okaru.dtomapper.converter.Converter;
import com.okaru.dtomapper.exception.ConversionException;

/**
 * Utlitiy methods to assist the Mapper class.
 * 
 * @author pokaru
 * 
 */
public class MapperUtils {
	/**
	 * Performs type conversion on the specified field within the specified
	 * object, if an Convert annotation is found on the field, then returns
	 * the field value.
	 * 
	 * @param field
	 * @param object
	 * @return
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public static Object convertFromObjectType(Field dtoField, Field objectField, Object object) {
		Convert convertAnnotation = dtoField.getAnnotation(Convert.class);
		try {
			if (convertAnnotation != null) {
				Class<? extends Converter<?, ?>> converterClass = convertAnnotation
						.converter();
				Converter converter = converterClass.newInstance();
				Object fieldValue = objectField.get(object);
				return converter.convertTo(converter.getConversionToClass().cast(fieldValue));
			} else {
				return objectField.get(object);
			}
		} catch (ClassCastException e) {
			throw new ConversionException("The conversion class specified was " +
					"invald for \"" + dtoField.getDeclaringClass().getName() + "." + 
					dtoField.getName() + "\"", e);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Performs type conversion on the specified field within the specified
	 * object, if an Convert annotation is found on the field, then returns
	 * the field value.
	 * 
	 * @param field
	 * @param object
	 * @return
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public static Object convertToObjectType(Field field, Object object) {
		Convert convertAnnotation = field.getAnnotation(Convert.class);
		try {
			if (convertAnnotation != null) {
				Class<? extends Converter<?, ?>> converterClass = convertAnnotation
						.converter();
				Converter converter = converterClass.newInstance();
				Object fieldValue = field.get(object);
				return converter.convertFrom(converter.getConversionFromClass().cast(fieldValue));
			} else {
				return field.get(object);
			}
		} catch (ClassCastException e) {
			throw new ConversionException("The conversion class specified was " +
					"invald for \"" + object.getClass().getName() + "." + 
					field.getName() + "\"", e);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Returns the specified method for the specified class.
	 * 
	 * @param someClass
	 * @param methodName
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public static Method getMethod(Class<?> someClass, String methodName,
			Class<?>... parameterTypes) throws NoSuchMethodException,
			SecurityException {
		if (someClass != null) {
			try {
				return someClass.getDeclaredMethod(methodName, parameterTypes);
			} catch (NoSuchMethodException e) {
				return getMethod(someClass.getSuperclass(), methodName);
			}
		} else {
			throw new NoSuchMethodException();
		}
	}

	/**
	 * Returns the specified field for the specified class.
	 * 
	 * @param someClass
	 * @param fieldName
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
	public static Field getField(Class<?> someClass, String fieldName)
			throws NoSuchFieldException, SecurityException {
		if (someClass != null) {
			try {
				return someClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
				return getField(someClass.getSuperclass(), fieldName);
			}
		} else {
			throw new NoSuchFieldException();
		}
	}

	/**
	 * Returns an array of all the fields contained in this class as well as
	 * fields in parent classes.
	 * 
	 * @param someClass
	 * @return
	 */
	public static Field[] getFields(Class<?> someClass) {
		List<Field> fieldList = new ArrayList<Field>();
		findFields(fieldList, someClass);
		return fieldList.toArray(new Field[fieldList.size()]);
	}

	/**
	 * Recursive utility method for finding fields in parent classes.
	 * 
	 * @param fieldList
	 * @param someClass
	 */
	private static void findFields(List<Field> fieldList, Class<?> someClass) {
		if (someClass != null) {
			Collections.addAll(fieldList, someClass.getDeclaredFields());
		}
		if (someClass.getSuperclass() != null) {
			findFields(fieldList, someClass.getSuperclass());
		}
	}

	/**
	 * Returns the class level mapping destination if there is one.
	 * 
	 * @param someDto
	 * @return
	 */
	public static String getClassLevelMappingDestination(Object someDto) {
		MappedObject dest = someDto.getClass()
				.getAnnotation(MappedObject.class);
		return (dest != null) ? dest.key() : null;
	}

	/**
	 * Returns the field-level mapping destination if there is one.
	 * 
	 * @param field
	 * @return
	 */
	public static String getFieldLevelMappingDestination(Field field) {
		MappedField mapping = field.getAnnotation(MappedField.class);
		if ((mapping != null) && (!mapping.mappedObjectKey().isEmpty())) {
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
		if ((mapping != null) && (!mapping.field().isEmpty())) {
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
	public static boolean mapsToField(Field field) {
		MappedField mapping = field.getAnnotation(MappedField.class);
		if ((mapping != null) && (mapping.mapsTo().equals(MapsTo.SETTER))) {
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
	public static boolean isIgnored(Field f) {
		return (f.getAnnotation(Ignore.class) != null);
	}

	/**
	 * Returns whether the specified field is an embedded dto object or not.
	 * 
	 * @param f
	 * @return
	 */
	public static boolean isEmbeddedDto(Field f) {
		return (f.getAnnotation(Embedded.class) != null);
	}

	/**
	 * A utility method for constructing a setter method from a field name.
	 * 
	 * @param f
	 * @return
	 */
	public static String getSetterMethodName(String fieldName) {
		return "set"
				+ new String(fieldName.charAt(0) + "").toUpperCase().concat(
						fieldName.substring(1));
	}

	/**
	 * A utility method for constructing a getter method from a field name.
	 * 
	 * @param f
	 * @return
	 */
	public static String getGetterMethodName(String fieldName) {
		return "get"
				+ new String(fieldName.charAt(0) + "").toUpperCase().concat(
						fieldName.substring(1));
	}
}
