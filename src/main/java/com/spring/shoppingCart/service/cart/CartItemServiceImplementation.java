package com.spring.shoppingCart.service.cart;

import java.math.BigDecimal;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.spring.shoppingCart.dto.CartItemDto;
import com.spring.shoppingCart.dto.ProductDto;
import com.spring.shoppingCart.exceptions.RecourceNotFoundException;
import com.spring.shoppingCart.model.Cart;
import com.spring.shoppingCart.model.CartItem;
import com.spring.shoppingCart.model.Product;
import com.spring.shoppingCart.repository.CartItemRepository;
import com.spring.shoppingCart.repository.CartRepository;
import com.spring.shoppingCart.service.product.ProductService;


@Service
public class CartItemServiceImplementation implements CartItemService{

	
	private final CartItemRepository cartItemRepository;
	private final CartRepository cartRepository;
    private final ProductService  productService ;
	private final CartService cartService;



	public CartItemServiceImplementation( CartItemRepository cartItemRepository,@Lazy CartService cartService, ProductService productService, CartRepository cartRepository) {
		super();
		this.cartItemRepository = cartItemRepository;
		this.cartRepository = cartRepository;
		this.productService = productService;
		this.cartService = cartService;
	
	}

	@Override
	public void addItemToCart(Long cartId, Long productId, int quantity) {
		// 1. Get cart
		Cart cart = cartService.getCartById(cartId);
	
		//2. Get product
		Product product = productService.getProductById(productId);
		
		//3. check if product is already exist in the cart
		//4. if No , initiate a new CartItem entry 
		//5. if yes , increase the quantity with the requested quantity
		
		CartItem cartItem = cart.getCartItems().stream()
				                     .filter(item -> item.getProduct().getId().equals(productId))
				                     .findFirst().orElse(new CartItem());
		
		  if(cartItem.getId() == null) {
				System.out.println(cartItem.getId());

			  cartItem.setCart(cart);
			  cartItem.setProduct(product);
			  cartItem.setQuantity(quantity);
			  cartItem.setUnitPrice(product.getPrice());
		  }else {
				System.out.println(cartItem.getId());
			  cartItem.setQuantity( cartItem.getQuantity() + quantity);
		  }
		  
		  cartItem.setTotalprice();
		  cart.addItem(cartItem);
		  cartItemRepository.save(cartItem);
		  cartRepository.save(cart);
	}

	@Override
	public void removeItemfromCart(long cartId, long productId) {
		Cart cart = cartService.getCartById(cartId);
		
		CartItem itemToRemove = getCartItem(cartId , productId);
		cart.deleteItem(itemToRemove);
		cartRepository.save(cart);
		
	}

	@Override
	public void updateItemQuantity(Long cartId, Long productId, int quantity) {
		
		Cart cart = cartService.getCartById(cartId);
		
         cart.getCartItems().stream().filter(item -> item.getProduct().getId().equals(productId))
                  .findFirst()
                  .ifPresent(item -> {
                	  
                	  item.setQuantity(quantity);
                	  item.setUnitPrice(item.getProduct().getPrice()) ;
                	  item.setTotalprice();
                  });
		BigDecimal totalAmount = cart.getCartItems().stream()
				                                          .map(CartItem :: getTotalPrice)
				                                          .reduce(BigDecimal.ZERO, BigDecimal::add);
		cart.setTotalAmount(totalAmount);
        
		cartRepository.save(cart);
		
	}
	
	@Override
	public CartItem  getCartItem(Long cartId, Long productId) {
		
		Cart cart = cartService.getCartById(cartId);
            
	return	cart.getCartItems().stream()
        .filter(item -> item.getProduct().getId().equals(productId))
        .findFirst().orElseThrow(() -> new RecourceNotFoundException("Item Not Found"));   
	}
	
	@Override
	public CartItemDto convertToCartItemDto(CartItem cartItem) {
		
		ProductDto productDto = productService.convertToDto(cartItem.getProduct());

		CartItemDto cartItemDto = new CartItemDto();
		
		cartItemDto.setId(cartItem.getId());
		cartItemDto.setProductDto(productDto);
		cartItemDto.setQuantity(cartItem.getQuantity());
		cartItemDto.setUnitPrice(cartItem.getUnitPrice());
		
		return cartItemDto;
		
		//return modelMapper.map(cartItem, CartItemDto.class);
		
	}
	
}
