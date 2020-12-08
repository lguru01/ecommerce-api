package com.rbc.ecommerce.config;

import java.security.SecureRandom;
import java.time.Clock;
import java.time.ZoneId;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;

import com.rbc.ecommerce.api.security.jwt.IJwtEncoder;
import com.rbc.ecommerce.api.security.jwt.impl.JwtEncoder;
import com.rbc.ecommerce.api.security.jwt.impl.JwtUserIdHelper;
import com.rbc.ecommerce.util.RequestBean;
import com.rbc.ecommerce.util.RequestFilter;
import com.rbc.ecommerce.util.RequestIdService;

@Configuration
public class EcommerceConfig {
	
	@Bean
	ZoneId zoneId(@Value("${application.timezone:UTC}") String timeZoneString) {
		return ZoneId.of(timeZoneString);
	}
	
	@Bean
	public Clock clock(ZoneId zoneId) {
		return Clock.system(zoneId);
	}
	
	@Bean
	@RequestScope
	public RequestBean requestBean() {
		return new RequestBean();
	}
	
	@Bean
	public RequestIdService requestIdService() {
		return new RequestIdService();
	}
	
	@Bean
	public SecureRandom secureRandom() {
		return new SecureRandom();
	}
	
	/**
	 * Randomly used some Encryption method.
	 */
	@Bean
	IJwtEncoder userIdJwtEncoder(Clock clock, SecureRandom secureRandom,
			@Value("${ecommerce.jwt.key:null}") String encryptionKey,
			@Value("${ecommerce.jwt.keyType:AES}") String encryptionKeyType,
			@Value("${ecommerce.jwt.alg:dir}") String encryptionAlg,
			@Value("${ecommerce.jwt.method:A192GCM}") String encryptionMethod) {
		JwtEncoder.Params params = new JwtEncoder.Params();
		params.setEncryptionAlgorithm(encryptionAlg);
		params.setEncryptionMethod(encryptionMethod);
		params.setEncryptionKeyType(encryptionKeyType);
		return new JwtEncoder(params, encryptionKey, secureRandom, clock);
	}
	
	@Bean
	JwtUserIdHelper jwtUserIdHelper(IJwtEncoder userIdJwtEncoder) {
		return new JwtUserIdHelper(userIdJwtEncoder);
	}
	
	@Bean
	public RequestFilter requestFilter(RequestBean requestBean, RequestIdService requestIdService, Clock clock, 
			JwtUserIdHelper jwtUserIdHelper) {
		return new RequestFilter(requestBean, requestIdService, clock, jwtUserIdHelper);
	}
	
	@Bean
	public FilterRegistrationBean<Filter> requestFilterRegistrationBean(RequestFilter requestFilter) {
		FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>(requestFilter);
		registrationBean.addUrlPatterns("/*");
		return registrationBean;
	}
}