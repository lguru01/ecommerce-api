package com.rbc.ecommerce.util;

import java.time.Instant;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class RequestBean {
	Instant requestTimestamp;
	private String uri;
	private String requestId;
	private String method;
	private String jwtToken;
	private String userId;
	public static final String JWT_TOKEN = "jwttoken";
	public static final String USER_ID = "userId";
	
	
	public static class InitParams {
		private Map<String, String> headers = new HashMap<>();
		
		public InitParams(HttpServletRequest request) {
			Enumeration<String> headerNames = request.getHeaderNames();
			while (headerNames.hasMoreElements()) {
				String name = headerNames.nextElement().toLowerCase();
				headers.put(name, request.getHeader(name));
			}
		}
		
		String getHeader(String name) {
			return headers.get(name.toLowerCase());
		}
	}
	
	public void initialize(Instant requestTimeStamp, String requestId, String uri, String method, InitParams initParams) {
		this.requestTimestamp = requestTimeStamp;
		this.requestId = requestId;
		this.uri = uri;
		this.method = method;
		this.jwtToken = initParams.getHeader(JWT_TOKEN);
		this.userId = initParams.getHeader(USER_ID);
	}

	public Instant getRequestTimestamp() {
		return requestTimestamp;
	}

	public String getUri() {
		return uri;
	}

	public String getRequestId() {
		return requestId;
	}
	
	public String getMethod() {
		return method;
	}
	
	public String getJwtToken() {
		return jwtToken;
	}
	
	public String getUserId() {
		return userId;
	}
}
