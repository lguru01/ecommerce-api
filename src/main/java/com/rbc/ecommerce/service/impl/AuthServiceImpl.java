package com.rbc.ecommerce.service.impl;

import org.springframework.stereotype.Service;

import com.rbc.ecommerce.exception.ResourceNotFoundException;
import com.rbc.ecommerce.mappers.UserMapper;
import com.rbc.ecommerce.model.User;
import com.rbc.ecommerce.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService{
	
	private final UserMapper userMapper;
	
	public AuthServiceImpl(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	@Override
	public User authenticate(String username, String password) {
		return userMapper.authenticateUser(username, password).orElseThrow(() -> new ResourceNotFoundException("User not Found"));
	}

}
