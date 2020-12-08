package com.rbc.ecommerce.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.rbc.ecommerce.api.ErrorResponse;
import com.rbc.ecommerce.api.GlobalError;
import com.rbc.ecommerce.util.RequestBean;

@RestControllerAdvice
public class EcommerceResponseEntityExceptionHandler {

	@Autowired
	private RequestBean requestBean;

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorResponse<Void> handleResourceNotFoundException(ResourceNotFoundException e) {
		return new ErrorResponse<>(new GlobalError(HttpStatus.NOT_FOUND.toString(), e.getMessage()), 
				requestBean.getRequestTimestamp(), requestBean.getRequestId());
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		return new ErrorResponse<>(new GlobalError(HttpStatus.BAD_REQUEST.toString(), e.getMessage()), 
				requestBean.getRequestTimestamp(), requestBean.getRequestId());
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ErrorResponse<Void> handleAccessDeniedException(AccessDeniedException e) {
		return new ErrorResponse<>(new GlobalError(HttpStatus.FORBIDDEN.toString(), e.getMessage()), 
				requestBean.getRequestTimestamp(), requestBean.getRequestId());
	}
	
	@ExceptionHandler(AuthenticationException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ErrorResponse<Void> handleAuthenticationException(AuthenticationException e) {
		return new ErrorResponse<>(new GlobalError(HttpStatus.FORBIDDEN.toString(), e.getMessage()), 
				requestBean.getRequestTimestamp(), requestBean.getRequestId());
	}
	
	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponse<Void> handleRuntimeException(RuntimeException e) {
		return new ErrorResponse<>(new GlobalError(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage()), 
				requestBean.getRequestTimestamp(), requestBean.getRequestId());
	}

}
