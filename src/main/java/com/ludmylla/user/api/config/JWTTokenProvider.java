package com.ludmylla.user.api.config;

import java.util.Date;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class JWTTokenProvider {

	private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(JWTTokenProvider.class);

	@Value(value = "${com.app.jwtsecret}")
	private String secretJwt;

	@Value(value = "${com.app.jwtexpiration}")
	private int expirationJwt;

	public String generateToken(Authentication authentication) {
		UserPrinicipal userPrinicipal = (UserPrinicipal) authentication.getPrincipal();

		Date now = new Date();
		Date expiration = new Date(now.getTime() + expirationJwt);

		return Jwts.builder().setSubject(Long.toString(userPrinicipal.getId())).setIssuedAt(new Date())
				.setExpiration(expiration).signWith(SignatureAlgorithm.HS512, secretJwt).compact();
	}

	public Long getTheUserId(String token) {
		Claims claims = Jwts.parser().setSigningKey(secretJwt).parseClaimsJws(token).getBody();
		return Long.valueOf(claims.getSubject());
	}

	public boolean validToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(secretJwt).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			LOGGER.error("Invalid JWT signature.");
		} catch (MalformedJwtException e) {
			LOGGER.error("Invalid JWT token.");
		} catch (ExpiredJwtException e) {
			LOGGER.error("Expired JWT token.");
		} catch (UnsupportedJwtException e) {
			LOGGER.error("Unsupported JWT token.");
		} catch (IllegalArgumentException e) {
			LOGGER.error("JWT claims string is empty.");
		}
		return false;
	}

}
