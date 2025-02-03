package com.spring.shoppingCart.controller;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;

import com.spring.shoppingCart.dto.CartDto;
import com.spring.shoppingCart.exceptions.RecourceNotFoundException;
import com.spring.shoppingCart.model.Cart;
import com.spring.shoppingCart.model.User;
import com.spring.shoppingCart.response.ApiResponse;
import com.spring.shoppingCart.service.cart.CartItemService;
import com.spring.shoppingCart.service.cart.CartService;
import com.spring.shoppingCart.service.user.UserService;

import io.jsonwebtoken.JwtException;

@RestController
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {
	
	private final CartItemService cartItemService;
	private final CartService cartService;
	private final UserService userService;

	public CartItemController(CartItemService cartItemService, CartService cartService, UserService userService) {
		super();
		this.cartItemService = cartItemService;
		this.cartService = cartService;
		this.userService = userService;
	}
	
	/***************************** Add item **********************************/
	
	@PostMapping("/item/add")
	public ResponseEntity<ApiResponse> addItemToCart(@RequestParam Long productId
			                                                                                     ,@RequestParam Integer quantity){
		
		try {
			
			User user = userService.getAuthenticatedUser();
			Cart	cart = cartService.initializeNewCart(user);
			
				cartItemService.addItemToCart(cart.getId(), productId, quantity);
				
				 //Cart cart2= cartService.getCartByUserId(user.getId());
				// CartDto  cartDto = cartService.convertCartToDto(cart2);
				
			return ResponseEntity.ok(new ApiResponse("Item Added Successfully .." , null));
			
		}catch (RecourceNotFoundException e) {
			
			return ResponseEntity.status(NOT_FOUND)
		             .body(new ApiResponse(e.getMessage() , null));
			
		}catch (JwtException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("Item Added Successfully .." , null));
		}
	}
	
	/***************************** Remove item from cart **********************************/
	
	@DeleteMapping("/cart/{cartId}/item/{itemId}/remove")
	public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId,@PathVariable Long itemId){
		
		try {
				cartItemService.removeItemfromCart( cartId , itemId);
				return ResponseEntity.ok(new ApiResponse("Item Removed Successfully .." , null));
		
	     }catch (RecourceNotFoundException e) {
		
				return ResponseEntity.status(NOT_FOUND)
			             .body(new ApiResponse(e.getMessage() , null));
			}
	}
	
	/********************************** update Item Quantity **************************************/
	
	@PutMapping("/cart/{cartId}/product/{productId}/updateQuantity")
	public ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable Long cartId
                                                                                                  ,@PathVariable Long productId
                                                                                                  ,@RequestParam Integer quantity){
		try {		
		cartItemService.updateItemQuantity(cartId, productId, quantity);
		return ResponseEntity.ok(new ApiResponse("Quantity updated Successfully .." , null));
		
	    }catch (RecourceNotFoundException e) {
		
				return ResponseEntity.status(NOT_FOUND)
			             .body(new ApiResponse(e.getMessage() , null));
			}
	}
	
	
	
}
