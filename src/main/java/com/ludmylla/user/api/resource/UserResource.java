package com.ludmylla.user.api.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ludmylla.user.api.mapper.UserMapper;
import com.ludmylla.user.api.model.JWTAuthenticationResponse;
import com.ludmylla.user.api.model.User;
import com.ludmylla.user.api.model.dto.UserCreateDTO;
import com.ludmylla.user.api.security.JWTTokenProvider;
import com.ludmylla.user.api.services.UserService;

@RestController
@RequestMapping("/users")
public class UserResource {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JWTTokenProvider jwtTokenProvider;

	@PostMapping("/signin")
	public ResponseEntity<JWTAuthenticationResponse> userAuthentication(@RequestBody User user) {
		// User user = UserMapper.INSTANCE.toUser(userLoginDTO);

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtTokenProvider.generateToken(authentication);
		return ResponseEntity.ok(new JWTAuthenticationResponse(token));
	}

	@PostMapping("/signup")
	public ResponseEntity<String> createUser(@RequestBody UserCreateDTO userCreateDTO) {
		User user = UserMapper.INSTANCE.toUser(userCreateDTO);
		userService.createUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully. ");
	}

}
