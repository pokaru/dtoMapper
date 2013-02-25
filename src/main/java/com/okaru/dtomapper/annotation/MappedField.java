package com.okaru.dtomapper.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Used to map a dto field to another object field at the field-level.  This
 * annotation's mappedObjectKey will take precedence over the class-level
 * MappedObject annotation if specified.
 * 
 * @author pokaru
 *
 */
@Target({FIELD})
@Retention(RUNTIME)
public @interface MappedField {
	/**
	 * This tells the mapper which key to use to find the object to map dto 
	 * values to/from.
	 * 
	 * @return
	 */
	String mappedObjectKey() default "";
	
	/**
	 * Optionally specifies the field in the mapped model to map this dto field
	 * to.  If nothing is specified the mapper will try to map the value of the
	 * annotated dto field to a field in the mapped object with the same name.
	 * 
	 * @return
	 */
	String field() default "";
	
	/**
	 * Optionally, specifies what this DTO's field value will be mapped to
	 * within the mapped object.  FIELD is the default.  If SETTER is selected,
	 * the field name will be used to find the setter for that field.  When the
	 * setter method for the mapped object field is found, that setter will be
	 * used to set the mapped object's value.
	 * 
	 * @return
	 */
	MapsTo mapsTo() default MapsTo.FIELD;
	
	/**
	 * (Optional) Tells the mapper whether or not to transfer null values.  By
	 * default, this attribute is set to false and null values are ignored.
	 * 
	 * @return
	 */
	boolean transferNulls() default false;
	
	public enum MapsTo {
		FIELD,
		SETTER
	}
}
