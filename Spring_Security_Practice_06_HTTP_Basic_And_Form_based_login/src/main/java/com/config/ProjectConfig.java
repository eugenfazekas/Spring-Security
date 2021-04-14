package com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomAuthenticationSuccesHandler customAuthenticationSuccesHandler;
	
	@Autowired
	private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
	
	@Override 
	protected void configure (HttpSecurity http) throws Exception {
		
		//http.formLogin().defaultSuccessUrl("/home",true);
		
		/*
		http.httpBasic(c -> {
								c.realmName("OTHER");
								c.authenticationEntryPoint(new CustomEntryPoint());
							}
					   );
		*/
				
				 http.formLogin()
		         .successHandler(customAuthenticationSuccesHandler)
		         .failureHandler(customAuthenticationFailureHandler)
		     .and()
		         .httpBasic();
		
		     http.authorizeRequests()
		             .anyRequest().authenticated();
		}
	
}
