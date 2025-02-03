package com.spring.shoppingCart.controller;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.math.BigDecimal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.shoppingCart.dto.CartDto;
import com.spring.shoppingCart.exceptions.RecourceNotFoundException;
import com.spring.shoppingCart.model.Cart;
import com.spring.shoppingCart.repository.CartRepository;
import com.spring.shoppingCart.response.ApiResponse;
import com.spring.shoppingCart.service.cart.CartService;

@RestController
@RequestMapping("${api.prefix}/carts")
public class CartController {
	
	private final CartService  cartService;
	private final CartRepository  cartRepository;
	
	
	
	public CartController(CartService cartService, CartRepository cartRepository) {
		super();
		this.cartService = cartService;
		this.cartRepository = cartRepository;
	}

	/***************************** Get Cart By Id  ***********************************/
	
    @GetMapping("/{id}/myCart")
	public ResponseEntity<ApiResponse> getCartById(@PathVariable  Long id){
		
		try {
			Cart cart = cartService.getCartById(id);
			CartDto cartDto =cartService.convertCartToDto(cart);
			return ResponseEntity.ok(new ApiResponse("Success" , cartDto));
			
		} catch (RecourceNotFoundException e) {
			
			return ResponseEntity.status(NOT_FOUND)
		             .body(new ApiResponse(e.getMessage() , null));
		}
	}
    
    /********************* Clear Cart **************************/
    
    @DeleteMapping("/{id}/clearCart")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable  Long id){
    	
    	try {
			cartService.clearCart(id);
			return ResponseEntity.ok(new ApiResponse("Clear Cart Success !! " , null));
			
		} catch (RecourceNotFoundException e) {
			
			return ResponseEntity.status(NOT_FOUND)
		             .body(new ApiResponse(e.getMessage() , null));
		}
    }
    
    /********************* Get Total Price **************************/
    
    @GetMapping("/{id}/myCart/totalPrice")
  	public ResponseEntity<ApiResponse> getTotalPriceOfCart(@PathVariable  Long id){
    	
    	try {
			BigDecimal totalPrice = cartService.getTotalPriceOfCart(id);
			return ResponseEntity.ok(new ApiResponse("Total Price  " , totalPrice));
			
		} catch (RecourceNotFoundException e) {
			
			return ResponseEntity.status(NOT_FOUND)
		             .body(new ApiResponse(e.getMessage() , null));
		}
    }
    
    
    
}
