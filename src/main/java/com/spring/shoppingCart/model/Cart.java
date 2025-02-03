package com.spring.shoppingCart.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Cart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private BigDecimal totalAmount = BigDecimal.ZERO;
	
	@OneToMany(mappedBy = "cart" , cascade = CascadeType.ALL , orphanRemoval = true) //if any cartItems have no reference to any Cart , will be remove
	private Set<CartItem> cartItems = new HashSet<>();
	
	@OneToOne
	@JoinColumn(name ="user_id")
	private User user;
	
	public void addItem(CartItem item) {
		
		this.cartItems.add(item);
		item.setCart(this);
		updateTotalPrice();
	}
	
	
	public void deleteItem(CartItem item) {
		
		this.cartItems.remove(item);
		item.setCart(null);
		updateTotalPrice();
	}
	

	public void updateTotalPrice() {

          this.totalAmount = this.cartItems.stream()
        		                        .map(item -> {
        		                        	
        		                        	BigDecimal unitPrice = item.getUnitPrice();
        		                        	if(unitPrice == null) {
        		                        		return BigDecimal.ZERO;
        		                        	}
        		                        	return unitPrice.multiply(BigDecimal.valueOf(item.getQuantity()));
        		                        	
        		                        }).reduce(BigDecimal.ZERO , BigDecimal::add);
	}


	

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Set<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(Set<CartItem> cartItems) {
		this.cartItems = cartItems;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}
	
	

}
