package com.rbc.ecommerce.api.security.jwt.impl;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rbc.ecommerce.api.security.jwt.IJwtEncoder;
import com.rbc.ecommerce.exception.AuthenticationException;

public class JwtUserIdHelper {
	private static final String CLAIM_ISSUER = "userId";
	private static final String CLAIM_DATA = "data";
	
	public static final ObjectMapper OM = new ObjectMapper()
			.registerModule(new JavaTimeModule())
			.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"))
			.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
	
	private final IJwtEncoder jwtEncoder;
	
	public JwtUserIdHelper(IJwtEncoder jwtEncoder) {
		this.jwtEncoder = jwtEncoder;
	}
	
	public String createJwt(String userId) {
		return jwtEncoder.encode(CLAIM_ISSUER, Collections.singletonMap(CLAIM_DATA, userId));
	}
	
	public String fromJwt(String token) {
		String userId;
		
		try {
			Map<String, Object> claims = jwtEncoder.decode(token);
			userId = OM.convertValue(claims.get(CLAIM_DATA), String.class);
		} catch (AuthenticationException e) {
			throw new AuthenticationException("Error decoding JWT ", e);
		}

		return userId;
	}
	
}
