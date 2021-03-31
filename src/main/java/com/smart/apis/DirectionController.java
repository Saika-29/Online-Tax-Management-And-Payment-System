package com.smart.apis;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DirectionController {
	
	@GetMapping("/emp/getall")
	public String goToEmployee() {
		
		
		
		return "Welcome to emp";
	}

}
