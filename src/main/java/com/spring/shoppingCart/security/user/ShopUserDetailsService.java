package com.spring.shoppingCart.security.user;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.shoppingCart.model.User;
import com.spring.shoppingCart.repository.UserRepository;


@Service
public class ShopUserDetailsService implements UserDetailsService{

	private final UserRepository userRepository;
		
	public ShopUserDetailsService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}


	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

              User user= Optional.ofNullable(userRepository.findByEmail(email))
                                           .orElseThrow(() -> new UsernameNotFoundException(" User Not Found !!"));
		
		return ShopUserDetails.buildUserDetails(user);
	}

	
	
}
