package com.spring.shoppingCart.service.cart;

import com.spring.shoppingCart.dto.CartItemDto;
import com.spring.shoppingCart.model.CartItem;

public interface CartItemService {
	
	
	void removeItemfromCart(long cartId , long productId);
	
	void updateItemQuantity(Long cartId , Long productId , int quantity);

	void addItemToCart(Long cartId, Long productId, int quantity);

	CartItem getCartItem(Long cartId, Long productId);

	CartItemDto convertToCartItemDto(CartItem cartItem);

}
