package com.ludmylla.user.api.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ludmylla.user.api.Mapper.UserMapper;
import com.ludmylla.user.api.model.JWTAuthenticationResponse;
import com.ludmylla.user.api.model.User;
import com.ludmylla.user.api.model.dto.UserLoginDTO;
import com.ludmylla.user.api.repository.RoleRepository;
import com.ludmylla.user.api.repository.UserRepository;
import com.ludmylla.user.api.security.JWTTokenProvider;

@RestController
@RequestMapping("/users")
public class UserResource {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JWTTokenProvider jwtTokenProvider;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@PostMapping("/signin")
	public ResponseEntity<JWTAuthenticationResponse> userAuthentication(@RequestBody UserLoginDTO userLoginDTO){
		User user = UserMapper.INSTANCE.toUser(userLoginDTO);
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token = jwtTokenProvider.generateToken(authentication);
		return ResponseEntity.ok(new JWTAuthenticationResponse(token));
	}
	
	//@PostMapping("/signup")
	//public ResponseEntity<String>

}
