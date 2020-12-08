package com.rbc.ecommerce.controller;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rbc.ecommerce.api.DataResponse;
import com.rbc.ecommerce.api.Response;
import com.rbc.ecommerce.api.security.PublicAccess;
import com.rbc.ecommerce.api.security.jwt.impl.JwtUserIdHelper;
import com.rbc.ecommerce.controller.dto.UserIdentificationRequest;
import com.rbc.ecommerce.controller.dto.UserIdentificationResponse;
import com.rbc.ecommerce.model.User;
import com.rbc.ecommerce.service.AuthService;
import com.rbc.ecommerce.util.RequestBean;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api("User Auth API")
public class AuthController {
	public static final String PATH_AUTHENTICATE = "/authenticate";
	
	private final AuthService authService;
	private final RequestBean requestBean;
	private final JwtUserIdHelper jwtUserIdHelper;
	
	public AuthController(AuthService authService, RequestBean requestBean, JwtUserIdHelper jwtUserIdHelper) {
		this.authService = authService;
		this.requestBean = requestBean;
		this.jwtUserIdHelper = jwtUserIdHelper;
	}
	
	@PublicAccess
	@PostMapping(value = PATH_AUTHENTICATE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation("Authenticate User")
	@ApiResponses(value = {@ApiResponse(code=400, message="Bad request", response = Response.class)})	
	public Response<UserIdentificationResponse> identify(@RequestBody @Valid UserIdentificationRequest request) {
		User user = this.authService.authenticate(request.getUserName(), request.getPassword());
		
		UserIdentificationResponse response = new UserIdentificationResponse();
		response.setStatus("SUCCESS");
		response.setToken(this.jwtUserIdHelper.createJwt(String.valueOf(user.getId())));
		
		return new DataResponse<>(response, requestBean.getRequestTimestamp(), requestBean.getRequestId());
	}
	
//	@AuthenticatedAccess
//	@GetMapping(value = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
//	public String getHello() {
//		return "Hello world";
//	}


}
