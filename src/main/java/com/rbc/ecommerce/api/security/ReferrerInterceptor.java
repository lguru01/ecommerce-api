package com.rbc.ecommerce.api.security;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.rbc.ecommerce.exception.AuthenticationException;

public class ReferrerInterceptor extends HandlerInterceptorAdapter{
	
	private static final Logger logger = LoggerFactory.getLogger(ReferrerInterceptor.class);

	private static final String HEADER_NAME = "referer";
	private static final String REFERER_PATTERN = "rbc.shopping.com";
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String referer = request.getHeader(HEADER_NAME);
		if(referer == null) {
			logger.info("Referer header is missing for {}", request.getRequestURL().toString());
			return true;
		}
		
		boolean isHostSafe = isHostSafe(referer);
		if(!isHostSafe) {
			throw new AuthenticationException("Invalid Host");
		}

		return isHostSafe;
	}
	
	private boolean isHostSafe(String urlString) {
		URL url;
		
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e) {
			return false;
		}
		
		String hostName = url.getHost();
		return Pattern.matches(REFERER_PATTERN, hostName);
	}
}
