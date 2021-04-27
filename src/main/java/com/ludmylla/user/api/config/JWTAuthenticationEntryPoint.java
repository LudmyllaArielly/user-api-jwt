package com.ludmylla.user.api.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;


@Component
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(JWTAuthenticationEntryPoint.class);
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		LOGGER.error("Responding with unauthorized erro. Message - {}", authException.getMessage());
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Sorry, you're not authorized to access this resource.");
	}

}
