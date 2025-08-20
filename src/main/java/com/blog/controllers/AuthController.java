package com.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.exceptions.ApiException;
import com.blog.payloads.JwtAuthRequest;
import com.blog.payloads.JwtAuthResponse;
import com.blog.payloads.UserDto;
import com.blog.repositories.RoleRepo;
import com.blog.security.JwtTokenHelper;
import com.blog.services.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request){
		
		this.doAutheticate(request.getUsername(),request.getPassword());
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
		String token= this.jwtTokenHelper.generateToken(userDetails);
		JwtAuthResponse jwtResponse = new JwtAuthResponse();
		jwtResponse.setToken(token);
		return new ResponseEntity<JwtAuthResponse>(jwtResponse,HttpStatus.OK);
		
	}

	private void doAutheticate(String email, String password) {
		UsernamePasswordAuthenticationToken token= new UsernamePasswordAuthenticationToken(email, password);
		 try {
			 this.authenticationManager.authenticate(token);


	        } catch (BadCredentialsException e) {
	        	System.out.println(" Invalid Username or Password  !!");
	            throw new ApiException(" Invalid Username or Password  !!");
	        }
		
	}
	
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto dto){
		UserDto new_dto=this.userService.registerNewUser(dto);
		return new ResponseEntity<UserDto>(new_dto,HttpStatus.CREATED);
	}
}
