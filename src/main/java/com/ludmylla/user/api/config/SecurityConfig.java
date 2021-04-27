package com.ludmylla.user.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ludmylla.user.api.security.JWTAuthenticationEntryPoint;
import com.ludmylla.user.api.security.JWTAuthenticationFilter;
import com.ludmylla.user.api.security.JWTTokenProvider;
import com.ludmylla.user.api.services.CustomUserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		//securedEnabled = true,
		//jsr250Enabled = true,
		prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private  CustomUserDetailsServiceImpl customUserDetailsServiceImpl;
	@Autowired
	private  JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	//@Autowired(required = true)
	//private final JWTAuthenticationFilter jwtAuthenticationFilter;
	/*
	@Autowired(required=true)
	public SecurityConfig(UserRepository userRepository, CustomUserDetailsServiceImpl customUserDetailsServiceImpl,
			JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint, JWTAuthenticationFilter jwtAuthenticationFilter) {

		this.customUserDetailsServiceImpl = customUserDetailsServiceImpl;
		this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}*/
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
		.exceptionHandling()
		.authenticationEntryPoint(jwtAuthenticationEntryPoint)
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.authorizeRequests()
		.antMatchers(HttpMethod.GET, "/**").permitAll()
		.anyRequest().authenticated();
		
		http.addFilterBefore(JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(customUserDetailsServiceImpl)
		.passwordEncoder(passwordEncoder());
	}
	
	@Bean
	public JWTAuthenticationFilter JWTAuthenticationFilter() {
	        return new JWTAuthenticationFilter();
	}
	
	@Bean
	public JWTTokenProvider JWTTokenProvider() {
	        return new JWTTokenProvider();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
