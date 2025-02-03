package com.spring.shoppingCart.controller;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.CONFLICT;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.shoppingCart.dto.UserDto;
import com.spring.shoppingCart.exceptions.AlreadyExistsException;
import com.spring.shoppingCart.exceptions.RecourceNotFoundException;
import com.spring.shoppingCart.model.CreateUserRequest;
import com.spring.shoppingCart.model.UpdateUserRequest;
import com.spring.shoppingCart.model.User;
import com.spring.shoppingCart.response.ApiResponse;
import com.spring.shoppingCart.service.user.UserService;

@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {
	
	private final UserService userService;
		
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}

    @GetMapping("/getUser/{id}")
	public ResponseEntity<ApiResponse> getUserById(@PathVariable Long id){
		
		try {
			User user = userService.getUserById(id);
			UserDto userDto = userService.convertUserToDto(user);
			
			return ResponseEntity.ok(new ApiResponse("User Added Successfully .." , userDto));
			
		} catch (RecourceNotFoundException e) {
			
			return ResponseEntity.status(NOT_FOUND)
		             .body(new ApiResponse(e.getMessage() , null));
		}
	}
    
    
    @PostMapping("/addUser")
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request){
    	
    	try {
    		
			User user = userService.createUser(request);
			UserDto userDto = userService.convertUserToDto(user);
			
			return ResponseEntity.ok(new ApiResponse("User Created Successfully .." , userDto));
			
		} catch (AlreadyExistsException e) {
			
			return ResponseEntity.status(CONFLICT)
		             .body(new ApiResponse(e.getMessage() , null));
		}
    }
    
    
    @PutMapping("/updateUser/{userId}")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UpdateUserRequest request ,@PathVariable Long userId){
    	
    	try {
    		
			User user = userService.updateUser(request,userId);
			UserDto userDto = userService.convertUserToDto(user);
			
			return ResponseEntity.ok(new ApiResponse("User Updated Successfully .." , userDto));
			
		} catch (RecourceNotFoundException e) {
			
			return ResponseEntity.status(NOT_FOUND)
		             .body(new ApiResponse(e.getMessage() , null));
		}
    }
    
    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId){
    	
    	try {
    		
			userService.deleteUser(userId);
			return ResponseEntity.ok(new ApiResponse("User Deleted Successfully .." , null));
			
		} catch (RecourceNotFoundException e) {
			
			return ResponseEntity.status(NOT_FOUND)
		             .body(new ApiResponse(e.getMessage() , null));
		}
    }
    
    
    
    

}
