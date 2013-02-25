package com.okaru.dtomapper.converter;

import com.okaru.dtomapper.exception.ConversionException;

@SuppressWarnings("rawtypes")
public abstract class ConverterFactory {
	
	protected abstract Converter createConverterInstance(Class<? extends Converter> converterClass);
	
	public Converter getConverter(Class<? extends Converter> converterClass){
		Converter converter = createConverterInstance(converterClass);
		if(converter == null){
			converter = createConverter(converterClass);
			if(converter == null){
				throw new ConversionException("No such converter: " + converterClass.getName());
			}
		}
		return converter;
	}
	
	private Converter createConverter(Class<? extends Converter> converterClass){
		try {
	        return converterClass.newInstance();
        } catch (InstantiationException e) {
	        e.printStackTrace();
        } catch (IllegalAccessException e) {
	        e.printStackTrace();
        }
		return null;
	}
}
