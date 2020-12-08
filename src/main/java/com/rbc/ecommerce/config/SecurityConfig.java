package com.rbc.ecommerce.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.rbc.ecommerce.api.security.ReferrerInterceptor;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig implements WebMvcConfigurer{
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new ReferrerInterceptor()).addPathPatterns("/shopping/**");
	}
}
