package com.ludmylla.user.api.model;

import lombok.Data;

@Data
public class JWTAuthenticationResponse {
	
	private String token;
	private String tokenType = "Bearer";
	
	public JWTAuthenticationResponse(String token) {
		this.token = token;
	}
}
