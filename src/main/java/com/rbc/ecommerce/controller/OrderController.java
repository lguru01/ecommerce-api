package com.rbc.ecommerce.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.rbc.ecommerce.api.DataResponse;
import com.rbc.ecommerce.api.Response;
import com.rbc.ecommerce.api.security.AuthenticatedAccess;
import com.rbc.ecommerce.model.OrderDetails;
import com.rbc.ecommerce.model.SaleItems;
import com.rbc.ecommerce.service.OrderService;
import com.rbc.ecommerce.util.RequestBean;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api("User Order Details API")
public class OrderController {
	public static final String GET_ORDERS_BY_USERID = "/orders/{userId}";
	public static final String GET_RECOMMENDATIONS_BY_USERID = "/recommendations/{userId}";
	
	private final OrderService orderService;
	private final RequestBean requestBean;
	
	public OrderController(OrderService orderService, RequestBean requestBean) {
		this.orderService = orderService;
		this.requestBean = requestBean;
	}	
	
	@AuthenticatedAccess
	@ApiOperation("Get order details of the User")
	@ApiResponses(value = {@ApiResponse(code=400, message="Bad request", response = Response.class)})
	@GetMapping(value = GET_ORDERS_BY_USERID, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<List<OrderDetails>> getUserOrders(@PathVariable("userId") long userId) {
		return new DataResponse<>(orderService.getOrdersByUserId(userId), requestBean.getRequestTimestamp(), 
				requestBean.getRequestId());
	}
	
	@AuthenticatedAccess
	@ApiOperation("Get recommended items for the User")
	@ApiResponses(value = {@ApiResponse(code=400, message="Bad request", response = Response.class)})
	@GetMapping(value = GET_RECOMMENDATIONS_BY_USERID, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<List<SaleItems>> getRecommendedProducts(@PathVariable("userId") long userId) {
		return new DataResponse<>(orderService.getRecommendedProducts(userId), requestBean.getRequestTimestamp(), 
				requestBean.getRequestId());
	}

}
