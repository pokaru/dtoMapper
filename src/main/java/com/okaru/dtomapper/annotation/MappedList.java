package com.okaru.dtomapper.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Denotes that the decorated field is a collection.
 * 
 * @author pokaru
 *
 */
@Target({FIELD})
@Retention(RUNTIME)
public @interface MappedList {

}
