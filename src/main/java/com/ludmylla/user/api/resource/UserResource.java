package com.ludmylla.user.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ludmylla.user.api.mapper.UserMapper;
import com.ludmylla.user.api.model.JWTAuthenticationResponse;
import com.ludmylla.user.api.model.User;
import com.ludmylla.user.api.model.dto.UserCreateDTO;
import com.ludmylla.user.api.model.dto.UserLoginDTO;
import com.ludmylla.user.api.services.UserService;

@RestController
@RequestMapping("/users")
public class UserResource {

	@Autowired
	private UserService userService;

	@PostMapping("/signin")
	public ResponseEntity<JWTAuthenticationResponse> userAuthentication(@RequestBody UserLoginDTO userLoginDTO) {
		try {
			User user = UserMapper.INSTANCE.toUser(userLoginDTO);
			String token = userService.userAuthentication(user);
			return ResponseEntity.ok(new JWTAuthenticationResponse(token));
		}catch (Exception e) {
			return new ResponseEntity<JWTAuthenticationResponse>(HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping("/signup")
	public ResponseEntity<String> createUser(@RequestBody UserCreateDTO userCreateDTO) {
		try {
			User user = UserMapper.INSTANCE.toUser(userCreateDTO);
			userService.createUser(user);
			return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully. ");
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(" Failed to create user: " + e.getMessage());
		}
	}
	
	@GetMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<User>> getAllUsers(){
		try {
			List<User> user = userService.getAllUsers();
			return ResponseEntity.ok(user);
		} catch (Exception e) {
			return new ResponseEntity<List<User>>(HttpStatus.BAD_REQUEST);
		} 
	}

}
