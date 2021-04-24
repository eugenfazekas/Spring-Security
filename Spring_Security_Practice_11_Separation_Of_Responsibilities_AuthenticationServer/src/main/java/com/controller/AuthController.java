package com.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entities.Otp;
import com.entities.User;
import com.service.UserService;

@RestController
@RequestMapping("user")
public class AuthController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("add")
	public void addUser(@RequestBody User user) {
		
		userService.addUser(user);
	}

	@PostMapping("auth")
	public void auth(@RequestBody User user) {
		
		userService.auth(user);
	}
	
	@PostMapping("otp/check")
	public void check(@RequestBody Otp otp, HttpServletResponse response) {
		if(userService.check(otp)) {
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		}
	}
}
