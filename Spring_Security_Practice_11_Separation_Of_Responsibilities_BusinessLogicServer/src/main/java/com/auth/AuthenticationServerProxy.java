package com.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.model.User;

@Component
public class AuthenticationServerProxy {

	@Autowired
	private RestTemplate rest;
	
	@Value("${auth.server.base.url}")
	private String baseURL;
	
	public void sendAuth(String username, String password) {
		String url = baseURL + "/user/auth";
		
		var body = new User();
		body.setUsername(username);
		body.setPassword(password);
		
		var request = new HttpEntity<>(body);
		rest.postForEntity(url, request, Void.class);
	}	
	
	public boolean sendOtp(String username, String code) {
		String url = baseURL + "/user/otp/check";
		
		var body = new User();
		body.setUsername(username);
		body.setCode(code);
		
		var request = new HttpEntity<>(body);
		rest.postForEntity(url, request, Void.class);
		
		var response = rest.postForEntity(url, request, Void.class);
		
		return response.getStatusCode().equals(HttpStatus.OK);
	}
}
