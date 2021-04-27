package com.ludmylla.user.api.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface CustomUserDetailsService {
	
	UserDetails loadUserByUsername(String email);
	
	UserDetails loadUserById(Long id);
}
