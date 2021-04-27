package com.ludmylla.user.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ludmylla.user.api.model.Role;
import com.ludmylla.user.api.model.User;
import com.ludmylla.user.api.repository.RoleRepository;
import com.ludmylla.user.api.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Long createUser(User user) {
		
		List<Role> role = roleRepository.findByName(user.getRoles().get(0).getName());
		user.setRoles(role);
		
		String password = passwordEncoder.encode(user.getPassword());
	
		user.setPassword(password);
		User userSave = userRepository.save(user);
		return userSave.getId();
	}

}
