package com.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@GetMapping("/test")
	public String getHello() {
		return "Get Hello!";
	}
	
	@PostMapping("/test")
	public String postHello() {
		return "Post Hello!";
	}
	
}
