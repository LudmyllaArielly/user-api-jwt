package com.ludmylla.user.api.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface CustomUserDetailsService {
	
	UserDetails loadUserByEmail(String email);
	
	UserDetails loadUserById(Long id);
}
