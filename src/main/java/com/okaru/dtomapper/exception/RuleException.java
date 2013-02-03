package com.okaru.dtomapper.exception;

@SuppressWarnings("serial")
public class RuleException extends RuntimeException {
	public RuleException() {
        super();
    }

    public RuleException(String s) {
        super(s);
    }
    
    public RuleException(String message, Throwable cause) {
        super(message, cause);
    }

    public RuleException(Throwable cause) {
        super(cause);
    }
}
