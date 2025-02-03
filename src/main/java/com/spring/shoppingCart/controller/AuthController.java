package com.spring.shoppingCart.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.shoppingCart.model.User;
import com.spring.shoppingCart.repository.UserRepository;
import com.spring.shoppingCart.request.LoginRequest;
import com.spring.shoppingCart.response.ApiResponse;
import com.spring.shoppingCart.response.JwtResponse;
import com.spring.shoppingCart.security.JWT.JwtUtils;
import com.spring.shoppingCart.security.user.ShopUserDetails;
import com.spring.shoppingCart.security.user.ShopUserDetailsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("${api.prefix}/auth")
public class AuthController {
	
	private final AuthenticationManager authenticationManager;
	private final ShopUserDetailsService userDetailsService ;
	private final UserRepository userRepository ;
	
	private final JwtUtils jwtUtils;

	public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils, ShopUserDetailsService userDetailsService, UserRepository userRepository) {
		super();
		this.authenticationManager = authenticationManager;
		this.userDetailsService = userDetailsService;
		this.userRepository = userRepository;
		this.jwtUtils = jwtUtils;
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<ApiResponse> login(@Valid  @RequestBody LoginRequest request){
		
		try {
			Authentication authentication = authenticationManager.authenticate(
					                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			// generate token for authenticated user
			UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
			String token = jwtUtils.generateToken(userDetails);
			
	      User user = userRepository.findByEmail(request.getEmail());
			
			JwtResponse response = new JwtResponse(
					              user.getId(),
					              token,
					              user.getRoles()
					              );
			
			return ResponseEntity.ok(new ApiResponse("Login Successfully !" ,response));
			
		} catch (AuthenticationException e) {
			
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
				                  	new ApiResponse(e.getMessage() , null));
		}
	}

}
