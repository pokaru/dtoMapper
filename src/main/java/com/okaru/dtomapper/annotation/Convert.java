package com.okaru.dtomapper.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.okaru.dtomapper.converter.Converter;

/**
 * This annotation denotes that a type conversion is to take place on the field
 * that it is applied to.
 * 
 * @author pokaru
 *
 */
@Target({FIELD})
@Retention(RUNTIME)
public @interface Convert {

	/**
	 * The conversion class that will perform the conversion.
	 * 
	 * @return
	 */
	Class<? extends Converter<?,?>> converter();
}
