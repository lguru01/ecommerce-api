package com.rbc.ecommerce.util;

import java.io.IOException;
import java.time.Clock;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

import com.rbc.ecommerce.api.security.jwt.impl.JwtUserIdHelper;

public class RequestFilter implements Filter {
	
	private static final Logger logger = LoggerFactory.getLogger(RequestFilter.class);

	public static final String ANONYMOUS = "(anonymous)";

	private final RequestBean requestBean;
	private final RequestIdService requestIdService;
	private final Clock clock;
	private JwtUserIdHelper jwtUserIdHelper;

	public RequestFilter(RequestBean requestBean, RequestIdService requestIdService, Clock clock,
			JwtUserIdHelper jwtUserIdHelper) {
		this.requestBean = requestBean;
		this.requestIdService = requestIdService;
		this.clock = clock;
		this.jwtUserIdHelper = jwtUserIdHelper;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;

		try {
			RequestBean.InitParams initParams = new RequestBean.InitParams(req);
			
			requestBean.initialize(clock.instant(), requestIdService.requestId(), req.getRequestURI(), req.getMethod(), initParams);

			// Compare the userId from token with the userId from the request header..
			Optional<String> tokenUserId = getTokenUserId(requestBean);			
			
			Authentication auth;
			if (tokenUserId.isPresent() && requestBean.getUserId().equals(tokenUserId.get())) {
				logger.debug("logged in user {}", tokenUserId);
				auth = new AuthenticationToken(requestBean.getUserId());
			} else {
				auth = new AnonymousAuthenticationToken(ANONYMOUS, ANONYMOUS, roles("ROLE_ANONYMOUS"));
			}

			SecurityContext ctx = new SecurityContextImpl();
			ctx.setAuthentication(auth);
			SecurityContextHolder.setContext(ctx);

			chain.doFilter(request, response);
		} finally {
			SecurityContextHolder.clearContext();
		}
	}

	public static class AuthenticationToken extends AbstractAuthenticationToken {

		private static final long serialVersionUID = 1L;
		
		private final String userId;

		public AuthenticationToken(String userId) {
			super(roles("ROLE_FULL_USER"));
			this.userId = userId;
		}

		@Override
		public boolean isAuthenticated() {
			return true;
		}

		@Override
		public Object getCredentials() {
			return null;
		}

		@Override
		public Object getPrincipal() {
			return userId;
		}

	}

	static List<GrantedAuthority> roles(String role) {
		return Collections.singletonList(new SimpleGrantedAuthority(role));

	}

	private Optional<String> getTokenUserId(RequestBean requestBean) {
		Optional<String> jwtToken = Optional.ofNullable(requestBean.getJwtToken());
		if (jwtToken.isPresent()) {
			return Optional.of(jwtUserIdHelper.fromJwt(jwtToken.get()));
		}

		return Optional.empty();
	}
}
