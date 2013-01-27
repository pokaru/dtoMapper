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
}
