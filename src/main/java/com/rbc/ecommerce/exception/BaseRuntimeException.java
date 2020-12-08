package com.rbc.ecommerce.exception;

public class BaseRuntimeException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final ErrorMessage errorMessage;
	
	public BaseRuntimeException(ErrorMessage errorMessage) {
		super(errorMessage.getMessage());
		this.errorMessage = errorMessage;
	}
	
	public BaseRuntimeException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = null;
	}
	
	public BaseRuntimeException(String message, Throwable cause) {
		super(message, cause);
		this.errorMessage = null;
	}
	
	public BaseRuntimeException(Throwable cause) {
		super(cause);
		this.errorMessage = null;
	}
	
	public ErrorMessage getErrorMessage() {
		return this.errorMessage;
	}
}
