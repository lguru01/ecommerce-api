package com.rbc.ecommerce.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.type.TypeReference;
import com.rbc.ecommerce.EcommerceApplication;
import com.rbc.ecommerce.api.DataResponse;
import com.rbc.ecommerce.api.ErrorResponse;
import com.rbc.ecommerce.api.ValidationErrorResponse;
import com.rbc.ecommerce.controller.dto.UserIdentificationRequest;
import com.rbc.ecommerce.controller.dto.UserIdentificationResponse;
import com.rbc.ecommerce.util.TestUtil;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:test.properties")
@AutoConfigureMockMvc
@SpringBootTest(classes = {EcommerceApplication.class})
public class AuthControllerTest {
	
	private static final TypeReference<DataResponse<UserIdentificationResponse>> TYPE_IDENTIFICATION_RESPONSE = 
			new TypeReference<DataResponse<UserIdentificationResponse>>() {};
			
	private static final TypeReference<ErrorResponse> TYPE_ERROR = 
			new TypeReference<ErrorResponse>() {};	
	
	private static final TypeReference<ValidationErrorResponse> TYPE_ERROR_VALIDATION = 
			new TypeReference<ValidationErrorResponse>() {};		
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void authenticate_success() throws Exception {
		MvcResult result = mockMvc.perform(
				post(AuthController.PATH_AUTHENTICATE)
				.content(TestUtil.writeValue(buildUserIdentificationRequest("maria@abc.com", "mariapass")))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andReturn();
		UserIdentificationResponse response = TestUtil.readValue(result, TYPE_IDENTIFICATION_RESPONSE).getData();
		assertEquals("SUCCESS", response.getStatus());
		assertNotNull(response.getToken());
	}
	
	@Test
	public void authenticate_failed() throws Exception {
		MvcResult result = mockMvc.perform(
				post(AuthController.PATH_AUTHENTICATE)
				.content(TestUtil.writeValue(buildUserIdentificationRequest("mariaabc.com", "mariapass")))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andReturn();
		ErrorResponse<Void> response = TestUtil.readValue(result, TYPE_ERROR);
		assertNotNull(response);
		assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
	}
	
	@Test
	public void authenticate_validationError() throws Exception {
		MvcResult result = mockMvc.perform(
				post(AuthController.PATH_AUTHENTICATE)
				.content(TestUtil.writeValue(buildUserIdentificationRequest("", "mariapass")))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andReturn();
		ValidationErrorResponse response = TestUtil.readValue(result, TYPE_ERROR_VALIDATION);
		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
	}
	
	private UserIdentificationRequest buildUserIdentificationRequest(String userName, String password) {
		UserIdentificationRequest request = new UserIdentificationRequest();
		request.setUserName(userName);
		request.setPassword(password);
		
		return request;
	}

}
