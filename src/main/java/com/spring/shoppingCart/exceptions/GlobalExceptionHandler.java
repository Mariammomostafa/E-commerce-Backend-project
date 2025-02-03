package com.spring.shoppingCart.exceptions;

import java.nio.file.AccessDeniedException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<String> handleAccessDeniedException( AccessDeniedException ex){
		
		String message = "You Don't have permission to access this page";
		
		return new ResponseEntity<>(message , HttpStatus.FORBIDDEN);
		
	}

}
