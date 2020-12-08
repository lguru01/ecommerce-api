package com.rbc.ecommerce.service;

import com.rbc.ecommerce.model.User;

public interface AuthService {
	User authenticate(String username, String password);

}
