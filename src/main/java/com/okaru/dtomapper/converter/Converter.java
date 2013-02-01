package com.okaru.dtomapper.converter;

import com.okaru.dtomapper.exception.ConversionException;


/**
 * Any class that extends this interface can be used as a type converter.
 * 
 * @author pokaru
 *
 * @param <F> From type
 * @param <T> To type
 */
public abstract class Converter<F,T> {
	
	/**
	 * Converts <code>someObject</code> from type F to type T.
	 * 
	 * @param someObject
	 * @return
	 */
	public abstract T convertFrom(F someObject) throws ConversionException;
	
	/**
	 * Converts <code>someObject</code> from type T to type F.
	 * 
	 * @param someObject
	 * @return
	 * @throws ConversionException
	 */
	public abstract F convertTo(T someObject) throws ConversionException;
	
	/**
	 * Returns the class this converter converts F objects to.
	 * 
	 * @return
	 */
	public abstract Class<T> getConversionToClass();

	/**
	 * Returns the class this converter converts objects from.
	 * 
	 * @return
	 */
	public abstract Class<F> getConversionFromClass();
}
