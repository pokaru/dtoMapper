package com.okaru.dtomapper.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.okaru.dtomapper.rule.Rule;

/**
 * An ordered list of mapping rules to be applied during the mapping process.
 * 
 * @author pokaru
 *
 */
@Target({TYPE})
@Retention(RUNTIME)
public @interface Rules {
	Class<? extends Rule<?>>[] value();
}
