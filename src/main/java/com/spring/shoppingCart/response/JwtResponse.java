package com.spring.shoppingCart.response;

import java.util.Collection;

import com.spring.shoppingCart.model.Role;

public class JwtResponse {
	
	private Long id;
	
	private String token ;
	
	private  Collection<Role> roles;

	public JwtResponse() {
		super();
	}
	
	public JwtResponse(Long id, String token, Collection<Role> roles) {
		super();
		this.id = id;
		this.token = token;
		this.roles = roles;
	}




	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	

}
