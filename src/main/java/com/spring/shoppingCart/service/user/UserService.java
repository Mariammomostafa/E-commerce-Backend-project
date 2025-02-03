package com.spring.shoppingCart.service.user;

import com.spring.shoppingCart.dto.UserDto;
import com.spring.shoppingCart.model.CreateUserRequest;
import com.spring.shoppingCart.model.UpdateUserRequest;
import com.spring.shoppingCart.model.User;

public interface UserService {
	
	 User getUserById(Long userId);
	 
	 User createUser(CreateUserRequest  request );
	 
	 User updateUser(UpdateUserRequest request,Long userId);
	 
	 void deleteUser(Long userId );

	UserDto convertUserToDto(User user);

	User getAuthenticatedUser();

}
