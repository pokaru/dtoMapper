package com.okaru.dtomapper.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Used to map a dto to another object at a class level.  The field-level,
 * MappedField, annotation will take precedence over this if specified.
 * 
 * @author pokaru
 *
 */
@Target({TYPE})
@Retention(RUNTIME)
public @interface MappedObject {
	/**
	 * The model field is mandatory.  This tells the mapper which class to map
	 * dto values to/from.
	 * 
	 * @return
	 */
	String key();
}
