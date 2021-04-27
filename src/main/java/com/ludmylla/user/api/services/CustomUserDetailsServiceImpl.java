package com.ludmylla.user.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ludmylla.user.api.config.UserPrinicipal;
import com.ludmylla.user.api.model.User;
import com.ludmylla.user.api.repository.UserRepository;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService, CustomUserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Transactional
	@Override
	public UserDetails loadUserByUsername(String email) {
		User user = userRepository.findByFistNameOrEmail(email, email).orElse(null);

		return UserPrinicipal.create(user);
	}
	
	@Transactional
	@Override
	public UserDetails loadUserById(Long id) {
		User user = userRepository.findById(id).orElse(null);
		return UserPrinicipal.create(user);
	}

	
}
