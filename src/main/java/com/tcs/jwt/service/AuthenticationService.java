package com.tcs.jwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tcs.jwt.config.JwtService;
import com.tcs.jwt.controller.AuthenticationRequest;
import com.tcs.jwt.controller.AuthenticationResponse;
import com.tcs.jwt.controller.RegisterRequest;
import com.tcs.jwt.repository.UserRepository;
import com.tcs.jwt.user.Role;
import com.tcs.jwt.user.User;

@Service
public class AuthenticationService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	public AuthenticationResponse register(RegisterRequest request) {

		User user = new User();

		user.setEmail(request.getEmail());
		user.setFirstname(request.getFirstname());
		user.setLastname(request.getLastname());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole(Role.USER);
		userRepository.save(user);
		String jwtToken = jwtService.generateToken(user);
		AuthenticationResponse authenticationResponse = new AuthenticationResponse();
		authenticationResponse.setResponse(jwtToken);
		return authenticationResponse;
	}

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
		String jwtToken = jwtService.generateToken(user);
		AuthenticationResponse authenticationResponse = new AuthenticationResponse();
		authenticationResponse.setResponse(jwtToken);
		return authenticationResponse;
	}

}
