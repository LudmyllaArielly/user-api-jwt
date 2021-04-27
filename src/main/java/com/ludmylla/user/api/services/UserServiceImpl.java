package com.ludmylla.user.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ludmylla.user.api.model.Role;
import com.ludmylla.user.api.model.User;
import com.ludmylla.user.api.repository.RoleRepository;
import com.ludmylla.user.api.repository.UserRepository;
import com.ludmylla.user.api.security.JWTTokenProvider;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JWTTokenProvider jwtTokenProvider;

	@Override
	public Long createUser(User user) {
		getRoleOfUser(user);
		getPasswordToEncrypt(user);
		User userSave = userRepository.save(user);
		return userSave.getId();
	}
	
	private void getRoleOfUser(User user) {
		List<Role> role = roleRepository.findByName(user.getRoles().get(0).getName());
		user.setRoles(role);
	}
	
	private void getPasswordToEncrypt(User user) {
		String password = passwordEncoder.encode(user.getPassword());
		user.setPassword(password);
	}

	@Override
	public String userAuthentication(User user) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return generateToken(authentication);
	}
	
	private String generateToken(Authentication authentication) {
		String token = jwtTokenProvider.generateToken(authentication);
		return token;
	}

}
