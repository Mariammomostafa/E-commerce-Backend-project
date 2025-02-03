package com.spring.shoppingCart.dto;

import java.math.BigDecimal;
import java.util.List;



public class CartDto {
	
	
     private Long id;
	
	private BigDecimal totalPrice ;
	
	private List<CartItemDto> cartItems;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<CartItemDto> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItemDto> cartItems) {
		this.cartItems = cartItems;
	}


	
	

}
