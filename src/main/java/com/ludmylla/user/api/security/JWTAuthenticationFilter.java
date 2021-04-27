package com.ludmylla.user.api.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ludmylla.user.api.services.CustomUserDetailsService;


public class JWTAuthenticationFilter extends OncePerRequestFilter{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JWTAuthenticationFilter.class);
	
	@Autowired
	private JWTTokenProvider jwtTokenProvider;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String jwt = getJwtFromRequest(request);
			
			if(StringUtils.hasText(jwt) && jwtTokenProvider.validToken(jwt)) {
				Long user = jwtTokenProvider.getTheUserId(jwt);
				
				UserDetails userDetails = customUserDetailsService.loadUserById(user);
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
			
		}catch (Exception e) {
			LOGGER.error("Could not set user authentication in security context", e);
		}
		
		filterChain.doFilter(request, response);
	}
	
	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());	
		}
		return null;
	}
	
	
}
