package com.rbc.ecommerce.api;

import org.springframework.lang.Nullable;

public class ValidationError {
	private String code;
	private String field;
	private Object rejectedValue;

	public ValidationError() {

	}

	public ValidationError(String code) {
		this.code = code;
	}

	public ValidationError(String code, String field) {
		this.code = code;
		this.field = field;
	}

	public ValidationError(String code, String field, @Nullable Object rejectedValue) {
		this.code = code;
		this.field = field;
		this.rejectedValue = rejectedValue;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Object getRejectedValue() {
		return rejectedValue;
	}

	public void setRejectedValue(Object rejectedValue) {
		this.rejectedValue = rejectedValue;
	}

}
