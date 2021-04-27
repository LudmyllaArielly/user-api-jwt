package com.ludmylla.user.api.services;

import java.util.List;

import com.ludmylla.user.api.model.User;

public interface UserService {
	
	Long createUser(User user);
	
	String userAuthentication(User user); 
	
	List<User> getAllUsers();

}
