package com.tcs.jwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

	@GetMapping("/api/demo")
	public ResponseEntity<String> sayHello(){
		return ResponseEntity.ok("Hello SIA");
	}
	
}
