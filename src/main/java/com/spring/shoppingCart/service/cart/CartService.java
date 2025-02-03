package com.spring.shoppingCart.service.cart;

import java.math.BigDecimal;

import com.spring.shoppingCart.dto.CartDto;
import com.spring.shoppingCart.model.Cart;
import com.spring.shoppingCart.model.User;


public interface CartService {
	
	Cart  getCartById(long id);
	
	BigDecimal getTotalPriceOfCart(long id);
	
	void clearCart(long id);

	Cart getCartByUserId(Long userId);

	/**************************** Initialize New Cart  ****************************************/
	Cart initializeNewCart(User user);

	CartDto convertCartToDto(Cart cart);

}
