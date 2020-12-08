package com.rbc.ecommerce.api;

import java.time.Instant;

public class ErrorResponse<T> implements Response<T> {

	private GlobalError error;
	private Instant timestamp;
	private String requestId;

	public ErrorResponse() {
	}

	public ErrorResponse(Instant timestamp, String requestId) {
		this.timestamp = timestamp;
		this.requestId = requestId;
	}

	public ErrorResponse(GlobalError error, Instant timestamp, String requestId) {
		this.error = error;
		this.timestamp = timestamp;
		this.requestId = requestId;
	}

	@Override
	public Instant getTimestamp() {
		return timestamp;
	}

	@Override
	public String getRequestId() {
		return requestId;
	}

	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public GlobalError getError() {
		return error;
	}

	public void setError(GlobalError error) {
		this.error = error;
	}

}
