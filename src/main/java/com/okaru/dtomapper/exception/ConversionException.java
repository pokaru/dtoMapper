package com.okaru.dtomapper.exception;

@SuppressWarnings("serial")
public class ConversionException extends RuntimeException {
	public ConversionException() {
        super();
    }

    public ConversionException(String s) {
        super(s);
    }
    
    public ConversionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConversionException(Throwable cause) {
        super(cause);
    }
}
