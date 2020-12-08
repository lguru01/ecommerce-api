package com.rbc.ecommerce.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Autowired
	private Environment env;

	@Bean
	public Docket docketApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage(env.getProperty("swagger.resourcePackage"))).build()
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfo(env.getProperty("swagger.apiInfo.title"), env.getProperty("swagger.apiInfo.description"),
				env.getProperty("swagger.apiInfo.version"), null, null, null, null, Collections.emptyList());
	}
}
