package com.spring.shoppingCart.security.user;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.spring.shoppingCart.model.User;
 

public class ShopUserDetails implements UserDetails{
	
			private Long id;
			
			private String email;
			
			private String firstName;
			
			private String password;
			
			private Collection<GrantedAuthority> authorities ;
			
			
			public static ShopUserDetails buildUserDetails(User user) {
				
				List<GrantedAuthority> authorities =user.getRoles().stream()
						                        .map(role -> new SimpleGrantedAuthority(role.getName()))
						                        .collect(Collectors.toList());
				
				return new ShopUserDetails(
						
						user.getId(),
						user.getEmail(),
						user.getFirstName(),
						user.getPassword(),
						authorities );
			}
	

	public ShopUserDetails() {
				super();
			}

	public ShopUserDetails(Long id, String email, String password, Collection<GrantedAuthority> authorities) {
				super();
				this.id = id;
				this.email = email;
				this.password = password;
				this.authorities = authorities;
			}
	
	


	public ShopUserDetails(Long id, String email, String firstName, String password,
			Collection<GrantedAuthority> authorities) {
		super();
		this.id = id;
		this.email = email;
		this.firstName = firstName;
		this.password = password;
		this.authorities = authorities;
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return this.authorities;
	}

	@Override
	public String getPassword() {
		
		return this.password;
	}

	@Override
	public String getUsername() {
	
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return UserDetails.super.isAccountNonExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return UserDetails.super.isAccountNonLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return UserDetails.super.isCredentialsNonExpired();
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return UserDetails.super.isEnabled();
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public void setAuthorities(Collection<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	
	
	
	
	
}
