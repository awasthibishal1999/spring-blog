package com.awasthi.bishal.Springblog.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.awasthi.bishal.Springblog.dto.LoginRequest;
import com.awasthi.bishal.Springblog.dto.RegisterRequest;
import com.awasthi.bishal.Springblog.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@RequestMapping("/signup")
	public ResponseEntity signup(@RequestBody RegisterRequest registerRequest) {
		authService.signup(registerRequest);
		return new ResponseEntity(HttpStatus.OK);
		
		
	}
	
	@RequestMapping("/login")
	public String login(@RequestBody LoginRequest loginRequest) {
		 return  authService.login(loginRequest);
		
		
	}

}
