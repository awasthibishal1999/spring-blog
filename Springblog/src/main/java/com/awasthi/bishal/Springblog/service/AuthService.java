package com.awasthi.bishal.Springblog.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.awasthi.bishal.Springblog.dto.LoginRequest;
import com.awasthi.bishal.Springblog.dto.RegisterRequest;
import com.awasthi.bishal.Springblog.model.User;
import com.awasthi.bishal.Springblog.repository.UserRepo;
import com.awasthi.bishal.Springblog.security.JwtProvider;

@Service
public class AuthService {
	
	
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
    private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtProvider jwtProvider;

	public void signup(RegisterRequest registerRequest) {
		User user = new User();
	    user.setUserName(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encodePassword(registerRequest.getPassword()));
        userRepo.save(user);

	
		
	}

	private String encodePassword(String password) {
	
		return passwordEncoder.encode(password);
	}

	 public AuthenticationResponse login(LoginRequest loginRequest) {
	        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
	        		(loginRequest.getUsername(),
	                loginRequest.getPassword()));
	        SecurityContextHolder.getContext().setAuthentication(authenticate);
	        String authenticationToken = jwtProvider.generatetoken(authenticate);
	        return new AuthenticationResponse(authenticationToken, loginRequest.getUsername());
	 }
	        			 

	public Optional<org.springframework.security.core.userdetails.User>getCurrentUser() {
		org.springframework.security.core.userdetails.User	principal =	(org.springframework.security.
				core.userdetails.User)SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		return Optional.of(	principal);
	}

}
