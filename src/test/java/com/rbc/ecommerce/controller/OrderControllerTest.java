package com.rbc.ecommerce.controller;

import static com.rbc.ecommerce.util.RequestBean.JWT_TOKEN;
import static com.rbc.ecommerce.util.RequestBean.USER_ID;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.List;

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
import com.rbc.ecommerce.controller.dto.UserIdentificationRequest;
import com.rbc.ecommerce.controller.dto.UserIdentificationResponse;
import com.rbc.ecommerce.model.OrderDetails;
import com.rbc.ecommerce.model.SaleItems;
import com.rbc.ecommerce.util.TestUtil;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:test.properties")
@AutoConfigureMockMvc
@SpringBootTest(classes = {EcommerceApplication.class})
public class OrderControllerTest {
		
	private static final TypeReference<DataResponse<UserIdentificationResponse>> TYPE_IDENTIFICATION_RESPONSE = 
			new TypeReference<DataResponse<UserIdentificationResponse>>() {};
	
	private static final TypeReference<DataResponse<List<OrderDetails>>> TYPE_ORDER_DETAILS = 
			new TypeReference<DataResponse<List<OrderDetails>>>() {};
			
	private static final TypeReference<ErrorResponse> TYPE_ERROR = 
			new TypeReference<ErrorResponse>() {};			
			
	private static final TypeReference<DataResponse<List<SaleItems>>> TYPE_SALE_ITEMS = 
					new TypeReference<DataResponse<List<SaleItems>>>() {};		
	
	@Autowired
	private MockMvc mockMvc;
	
	
	@Test
	public void getRecommendedItems_success() throws Exception {
	
		UserIdentificationResponse response = authenticate();
		
		MvcResult result1 = mockMvc.perform(
				get(OrderController.GET_RECOMMENDATIONS_BY_USERID, 1)
				.header(USER_ID, 1)
				.header(JWT_TOKEN, response.getToken())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andReturn();
		List<SaleItems> saleItems = TestUtil.readValue(result1, TYPE_SALE_ITEMS).getData();
		assertNotNull(saleItems);
		assertEquals(2, saleItems.size());
	}
	
	@Test
	public void getRecommendedItems_AccessDenied() throws Exception {
			MvcResult result = mockMvc.perform(
					get(OrderController.GET_RECOMMENDATIONS_BY_USERID, 1)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON))
					.andReturn();
		ErrorResponse<Void> response = TestUtil.readValue(result, TYPE_ERROR);
		assertNotNull(response);
		assertEquals(HttpStatus.FORBIDDEN.value(), result.getResponse().getStatus());
	}
	
	@Test
	public void getRecommendedItems_InvalidUser() throws Exception {
		//Authenticate with first user and getOrder invoked by second user.
		UserIdentificationResponse response = authenticate();
		
		MvcResult result1 = mockMvc.perform(
				get(OrderController.GET_RECOMMENDATIONS_BY_USERID, 2)
				.header(USER_ID, 2)
				.header(JWT_TOKEN, response.getToken())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andReturn();
		ErrorResponse<Void> errorResponse = TestUtil.readValue(result1, TYPE_ERROR);
		assertNotNull(errorResponse);
		assertEquals(HttpStatus.FORBIDDEN.value(), result1.getResponse().getStatus());
	}
	
	private UserIdentificationResponse authenticate() throws Exception {
		MvcResult result = mockMvc.perform(
				post(AuthController.PATH_AUTHENTICATE)
				.content(TestUtil.writeValue(buildUserIdentificationRequest("maria@abc.com", "mariapass")))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andReturn();
		return TestUtil.readValue(result, TYPE_IDENTIFICATION_RESPONSE).getData();
	}
	
	private UserIdentificationRequest buildUserIdentificationRequest(String userName, String password) {
		UserIdentificationRequest request = new UserIdentificationRequest();
		request.setUserName(userName);
		request.setPassword(password);
		
		return request;
	}
}
