package com.okaru.dtomapper.exception;

@SuppressWarnings("serial")
public class MapperException extends RuntimeException {
	public MapperException() {
        super();
    }

    public MapperException(String s) {
        super(s);
    }
    
    public MapperException(String message, Throwable cause) {
        super(message, cause);
    }

    public MapperException(Throwable cause) {
        super(cause);
    }
}
