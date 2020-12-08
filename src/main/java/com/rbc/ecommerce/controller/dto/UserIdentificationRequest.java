package com.rbc.ecommerce.controller.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserIdentificationRequest {
	
	@NotBlank
	@Size(min = 1, max = 45, message = "Username must be between 1 and 45 characters")
	String userName;
	
	@NotBlank
	@Size(min = 9, max = 9, message = "Password must be between 1 and 45 characters")
	private String password;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
