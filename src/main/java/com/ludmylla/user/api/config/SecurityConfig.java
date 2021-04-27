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
import org.springframework.security.config.annotation.web.builders.WebSecurity;
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
		.antMatchers( 
				"/swagger-resources/**",
	            "/swagger-ui.html",
	            "/v2/api-docs",
	            "/webjars/**").permitAll()
		.antMatchers(HttpMethod.GET, "/**").permitAll()
		.antMatchers(HttpMethod.POST, "/users/signin/**").permitAll()
		.antMatchers(HttpMethod.POST, "/users/signup/**").permitAll()
		.antMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
		.anyRequest().authenticated();

		http.addFilterBefore(JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	
	@Override
	  public void configure(WebSecurity web) throws Exception {
	    web.ignoring().antMatchers("/v2/api-docs/**", "/v2/api-docs/swagger-config", "/swagger-ui/**", "/v2/api-docs.yml",
	        "/configuration/ui", "/swagger-resources/**", "/configuration/security", "/swagger-ui.html", "/webjars/**");

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
