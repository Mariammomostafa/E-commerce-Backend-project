package com.spring.shoppingCart.service.user;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.shoppingCart.dto.CartDto;
import com.spring.shoppingCart.dto.OrderDto;
import com.spring.shoppingCart.dto.UserDto;
import com.spring.shoppingCart.exceptions.AlreadyExistsException;
import com.spring.shoppingCart.exceptions.RecourceNotFoundException;
import com.spring.shoppingCart.model.CreateUserRequest;
import com.spring.shoppingCart.model.UpdateUserRequest;
import com.spring.shoppingCart.model.User;
import com.spring.shoppingCart.repository.UserRepository;
import com.spring.shoppingCart.service.cart.CartService;
import com.spring.shoppingCart.service.order.OrderService;

@Service
public class UserServiceImplementation implements UserService{

	
	private final UserRepository userRepository;
	private final CartService cartService;
	private final OrderService orderService;
	private final PasswordEncoder  passwordEncoder;
		
	public UserServiceImplementation(UserRepository userRepository, CartService cartService, OrderService orderService, PasswordEncoder passwordEncoder) {
		super();
		this.userRepository = userRepository;
		this.cartService = cartService;
		this.orderService = orderService;
		this.passwordEncoder = passwordEncoder;
	}


	@Override
	public User getUserById(Long userId) {
		
		return userRepository.findById(userId)
				                 .orElseThrow(() -> new RecourceNotFoundException("User Not Found !! "));
	}

	
	@Override
	public void deleteUser(Long userId) {
		
		userRepository.findById(userId)
		                             .ifPresentOrElse(userRepository::delete , 
		                            		                     () -> {
		                            		                    	 throw new RecourceNotFoundException("User Not Found !! ");
		                            		                    	 });
		
	}

	@Override
	public User createUser(CreateUserRequest request) {
		
		return Optional.of(request)
				          .filter(user  -> ! userRepository.existsByEmail(request.getEmail()))
				          .map(req  -> {
				        	  
				        	  User user = new User();
				        	  
				        	  user.setFirstName(request.getFirstName());
				        	  user.setLastName(request.getLastName());
				        	  user.setEmail(request.getEmail());
				        	  user.setPassword(passwordEncoder.encode(request.getPassword()));
				        	  
				        	   return userRepository.save(user);
				        	   
				          }).orElseThrow(() -> new AlreadyExistsException("User already exists !!!"));
	}

	@Override
	public User updateUser(UpdateUserRequest request ,Long userId) {
		
		return userRepository.findById(userId).map(existingUser -> {
			
			                               existingUser.setFirstName(request.getFirstName());
			                               existingUser.setLastName(request.getLastName());
			                               
			                               return userRepository.save(existingUser) ;
			                               
		                                     }).orElseThrow(() -> new RecourceNotFoundException("User Not Found !! "));
	}
	
	
	
	@Override
	public UserDto convertUserToDto(User user) {
		
		CartDto cartDto =cartService.convertCartToDto(user.getCart());		
		
		List<OrderDto> orderDtos = user.getOrders().stream()
				                                              .map(order -> orderService.convertToDto(order))
				                                              .toList();
		
		UserDto dto = new UserDto();
		dto.setId(user.getId());
		dto.setCartDto(cartDto);
		dto.setFirstName(user.getFirstName());
		dto.setLastName(user.getLastName());
		dto.setEmail(user.getEmail());
		dto.setOrders(orderDtos);
		
		
		return dto;
	}


	@Override
	public User getAuthenticatedUser() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		 String email=  authentication.getName();
		 
		 System.out.println(email);
		 
		return userRepository.findByEmail(email);
	}
	
	
	
	
	
	
	
	
}
