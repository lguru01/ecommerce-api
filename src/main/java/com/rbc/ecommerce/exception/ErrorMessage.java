package com.rbc.ecommerce.exception;

public enum ErrorMessage {
	USER_NOT_FOUND("ERR01", "User not found");
	
	private final String errorCode;
	private final String message;
	
	ErrorMessage(String errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	
	public String getMessage() {
		return message;
	}
}
