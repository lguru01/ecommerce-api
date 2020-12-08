package com.rbc.ecommerce.api.security.jwt;

import java.util.Map;

import com.rbc.ecommerce.exception.AuthenticationException;

public interface IJwtEncoder {
	String encode(String issuer, Map<String, Object> claims) throws AuthenticationException;
	
	Map<String, Object> decode(String data) throws AuthenticationException;
}
