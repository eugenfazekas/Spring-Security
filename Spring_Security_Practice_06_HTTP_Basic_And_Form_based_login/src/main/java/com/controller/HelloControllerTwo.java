package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloControllerTwo {

	@GetMapping("/home")
    public String home() {

        return "home.html";
    }
}
