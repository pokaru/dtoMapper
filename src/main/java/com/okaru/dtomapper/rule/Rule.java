package com.okaru.dtomapper.rule;

import java.util.Map;

/**
 * Defines a contract for a rule.
 * 
 * @author pokaru
 *
 */
public abstract class Rule<T> {

	/**
	 * Applies the rule to the specified dto.  <code>objectMap</code> is
	 * is <b>read-only</b> and provides access to all the mapped objects.  This
	 * allows for further control and customization over mappings.
	 * 
	 * @param someDto
	 * @param objectMap
	 */
	public abstract void apply(T someDto, Map<String, Object> objectMap);
}
