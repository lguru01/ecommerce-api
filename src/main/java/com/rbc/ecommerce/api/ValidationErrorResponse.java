package com.rbc.ecommerce.api;

import java.time.Instant;
import java.util.Collection;

import org.springframework.lang.Nullable;

public class ValidationErrorResponse<T> extends ErrorResponse<ValidationError> {

	private Collection<ValidationError> validationError;

	public ValidationErrorResponse() {
	}

	public ValidationErrorResponse(Instant timestamp, String requestId, GlobalError error,
			Collection<ValidationError> validationError) {
		super(error, timestamp, requestId);
		this.validationError = validationError;
	}
	
	@Nullable
	@Override
	public Collection<ValidationError> getValidationError() {
		return validationError;
	}

	public void setValidationError(Collection<ValidationError> validationError) {
		this.validationError = validationError;
	}

}
