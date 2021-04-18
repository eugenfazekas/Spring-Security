package com.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	
	  @GetMapping("/hello")
	    public String hello() {
	        return "Hello!";
	    }
	  
	  @GetMapping("/deny")
	    public String deny() {
	        return "Hello!";
	    }
	  
	  @GetMapping("/hello_a")
	    public String getHelloA() {
	        return "Hello_get!";
	    }
	  
	  @PostMapping("/hello_a")
	    public String postHelloA() {
	        return "Hello_post!";
	    }
	  
	  @GetMapping("/product/{code}")
	    public String productCode(@PathVariable String code) {
	        return code;
	    }
	  
	  @GetMapping("/email/{email}")
	    public String video1(@PathVariable String email) {
	        return "Allowed for email " + email;
	    }
	  
	  @GetMapping("/video/{country}/{language}")
	    public String video2(@PathVariable String country,@PathVariable String language) {
	        return "Video allowed for "+country + " " + language;
	    }
}
