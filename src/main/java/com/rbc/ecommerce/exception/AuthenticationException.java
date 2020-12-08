package com.rbc.ecommerce.exception;

public class AuthenticationException extends BaseRuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AuthenticationException(ErrorMessage errorMessage) {
		super(errorMessage.getMessage());
	}
	
	public AuthenticationException(String errorMessage) {
		super(errorMessage);
	}
	
	public AuthenticationException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public AuthenticationException(Throwable cause) {
		super(cause);
	}
	

}
