package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.filters.AuthenticationLoggingFilter;
import com.filters.RequestValidationFilter;
import com.filters.StaticKeyAuthenticationFilter;

@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private StaticKeyAuthenticationFilter staticKeyAuthenticationFilter;
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(
                new RequestValidationFilter(),
                BasicAuthenticationFilter.class)
        	.addFilterAt(staticKeyAuthenticationFilter,
                BasicAuthenticationFilter.class)
            .addFilterAfter(
                new AuthenticationLoggingFilter(),
                BasicAuthenticationFilter.class)
            .authorizeRequests()
                .anyRequest()
                    .permitAll();
    }
}
