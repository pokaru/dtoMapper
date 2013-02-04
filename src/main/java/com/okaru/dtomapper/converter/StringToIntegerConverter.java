package com.okaru.dtomapper.converter;

import com.okaru.dtomapper.exception.ConversionException;


/**
 * Converts Strings to Integers
 * 
 * @author pokaru
 *
 */
public class StringToIntegerConverter extends Converter<String, Integer> {

	@Override
	public Integer convertFrom(String someObject){
		try{
			return Integer.valueOf(someObject);
		} catch (NumberFormatException e){
			throw new ConversionException(e);
		}
	}

	@Override
	public String convertTo(Integer someObject) throws ConversionException {
		return someObject.toString();
	}

	@Override
	public Class<Integer> getConversionToClass() {
		return Integer.class;
	}

	@Override
	public Class<String> getConversionFromClass() {
		return String.class;
	}
}
