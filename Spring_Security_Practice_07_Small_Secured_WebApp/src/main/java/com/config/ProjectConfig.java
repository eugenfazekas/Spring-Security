package com.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import com.service.AuthenticationProviderService;

@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private AuthenticationProviderService authenticationProviderService;
	
	@Bean 
	public PasswordEncoder passwordEncoder() {
		 Map<String,PasswordEncoder> encoders = new HashMap<>();
		 
		 encoders.put("bcrypt", new BCryptPasswordEncoder());
		 encoders.put("scrypt", new SCryptPasswordEncoder());
		 
		 return new DelegatingPasswordEncoder("scrypt", encoders);
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(authenticationProviderService);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests().antMatchers("/db","/db/**").permitAll();
		http.formLogin().defaultSuccessUrl("/main", true)
		.and()
				.authorizeRequests().anyRequest().authenticated()
		.and()
				.csrf().ignoringAntMatchers("/db","/db/**")
		.and()
				.headers().frameOptions().sameOrigin();		
	}	
}
