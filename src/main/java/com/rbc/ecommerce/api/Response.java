package com.rbc.ecommerce.api;

import java.time.Instant;
import java.util.Collection;

import org.springframework.lang.Nullable;

public interface Response<T> {
	Instant getTimestamp();
	String getRequestId();
	
	@Nullable
	default T getData() {
		return null;
	}
	
	@Nullable
	default GlobalError getError() {
		return null;
	}
	
	@Nullable
	default Collection<ValidationError> getValidationError() {
		return null;
	}
}
