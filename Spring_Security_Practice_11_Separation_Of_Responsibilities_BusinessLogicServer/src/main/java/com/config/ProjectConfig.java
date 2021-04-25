package com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

import com.auth.OtpAuthenticationProvider;
import com.auth.UsernamePasswordAuthenticationProvider;
import com.filters.InitialAuthenticationFilter;
import com.filters.JwtAuthenticationFilter;

@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Autowired
	private InitialAuthenticationFilter initialAuthenticationFilter;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Autowired
	private UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;
	
	@Autowired
	private OtpAuthenticationProvider otpAuthenticationProvider;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		
		auth
			.authenticationProvider(usernamePasswordAuthenticationProvider)
			.authenticationProvider(otpAuthenticationProvider);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable();
		
		http
		.addFilterAt(initialAuthenticationFilter,BasicAuthenticationFilter.class)
		.addFilterAfter(jwtAuthenticationFilter, BasicAuthenticationFilter.class);
		
		http.authorizeRequests().anyRequest().authenticated();
	}
	
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		
		return super.authenticationManager();
	}
	
}
