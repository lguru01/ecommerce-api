package com.rbc.ecommerce.exception;

public class ResourceNotFoundException extends BaseRuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ResourceNotFoundException(ErrorMessage errorMessage) {
		super(errorMessage.getMessage());
	}
	
	public ResourceNotFoundException(String errorMessage) {
		super(errorMessage);
	}
	
	public ResourceNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ResourceNotFoundException(Throwable cause) {
		super(cause);
	}
	

}
