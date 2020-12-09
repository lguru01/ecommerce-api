package com.rbc.ecommerce.api;

import java.time.Instant;

import org.springframework.lang.Nullable;

public class DataResponse<T> implements Response<T> {

	private T data;
	private Instant timestamp;
	private String requestId;

	public DataResponse() {
	}

	public DataResponse(Instant timestamp, String requestId) {
		this.timestamp = timestamp;
		this.requestId = requestId;
	}

	public DataResponse(T data, Instant timestamp, String requestId) {
		this.data = data;
		this.timestamp = timestamp;
		this.requestId = requestId;
	}

	@Override
	public Instant getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String getRequestId() {
		return requestId;
	}	

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	
	@Nullable
	@Override
	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
