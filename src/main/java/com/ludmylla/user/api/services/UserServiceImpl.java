package com.ludmylla.user.api.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ludmylla.user.api.model.User;
import com.ludmylla.user.api.repository.RoleRepository;
import com.ludmylla.user.api.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Long createUser(User user) {
		User userSave = userRepository.save(user);
		return userSave.getId();
	}

}
