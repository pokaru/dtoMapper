package com.okaru.dtomapper.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * This annotation is used to denote that a field is actually an embedded DTO
 * and should be treated as such by the <code>Mapper</code>.
 * 
 * @author pokaru
 *
 */
@Target({FIELD})
@Retention(RUNTIME)
public @interface Embedded {

}
