package com.spring.shoppingCart.service.cart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.shoppingCart.dto.CartDto;
import com.spring.shoppingCart.dto.CartItemDto;
import com.spring.shoppingCart.exceptions.RecourceNotFoundException;
import com.spring.shoppingCart.model.Cart;
import com.spring.shoppingCart.model.CartItem;
import com.spring.shoppingCart.model.User;
import com.spring.shoppingCart.repository.CartItemRepository;
import com.spring.shoppingCart.repository.CartRepository;

@Service
public class CartServiceImplementation implements CartService{
	
	private final CartRepository cartRepository;
	private final CartItemRepository cartItemRepository;
	private final CartItemService cartItemService;

	
	public CartServiceImplementation(CartRepository cartRepository, CartItemRepository cartItemRepository,  CartItemService cartItemService) {
		super();
		this.cartRepository = cartRepository;
		this.cartItemRepository = cartItemRepository;
		this.cartItemService = cartItemService;

	}

	@Override
	public Cart getCartById(long id) {
		
		Cart cart = cartRepository.findById(id)
				             .orElseThrow(() -> new RecourceNotFoundException("Cart Not Found !! "));
		
		//BigDecimal totalPrice = cart.getTotalAmount();
		//cart.setTotalAmount(totalPrice);
		//return cartRepository.save(cart);
		
		return cart;
	}

	@Override
	public BigDecimal getTotalPriceOfCart(long id) {
		
		Cart cart = getCartById(id);
		BigDecimal totalPrice = cart.getTotalAmount();
		return totalPrice;
	}

	
	@Transactional
	@Override
	public void clearCart(long id) {
		
		Cart cart = getCartById(id);
		
		cartItemRepository.deleteAllByCartId(id);
		cart.getCartItems().clear();
		
		cartRepository.delete(cart);
	}
	
	
	/**************************** Initialize New Cart  ****************************************/
    
	 @Override
    public Cart initializeNewCart(User user) {
    	
		 return Optional.ofNullable(getCartByUserId(user.getId()))
				          .orElseGet(() ->{
				        	  
				        	  Cart cart =new Cart();
				        	  cart.setUser(user);
				        	  return  cartRepository.save(cart);
				          });
		     }
	 

	@Override
	public Cart getCartByUserId(Long userId) {
		
		return cartRepository.findByUserId(userId);
	}
	
	
	@Override
	public CartDto convertCartToDto(Cart cart) {
	    
		 Set<CartItem>  cartItemSet=   cart.getCartItems();
		List<CartItem> cartItems =  new ArrayList<>(cartItemSet);
		
		List<CartItemDto> cartItemDtos = cartItems
										                                     .stream()
										                                    .map(cartItem -> 
										                                    cartItemService.convertToCartItemDto(cartItem)).toList();

		CartDto cartDto =new CartDto();
		
		cartDto.setId(cart.getId());
		cartDto.setTotalPrice(cart.getTotalAmount());
		cartDto.setCartItems( cartItemDtos);
		
		return cartDto;
		//return modelMapper.map(cart, CartDto.class);
	}

}
