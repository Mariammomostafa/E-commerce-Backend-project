package com.spring.shoppingCart.service.order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.spring.shoppingCart.dto.OrderDto;
import com.spring.shoppingCart.dto.OrderItemsDto;
import com.spring.shoppingCart.enums.OrderStatus;
import com.spring.shoppingCart.exceptions.RecourceNotFoundException;
import com.spring.shoppingCart.model.Cart;
import com.spring.shoppingCart.model.CartItem;
import com.spring.shoppingCart.model.Order;
import com.spring.shoppingCart.model.OrderItems;
import com.spring.shoppingCart.model.Product;
import com.spring.shoppingCart.repository.OrderRepository;
import com.spring.shoppingCart.repository.ProductRepository;
import com.spring.shoppingCart.service.cart.CartService;

@Service
public class OrderServiceImplementation implements OrderService {

	private final OrderRepository orderRepository;
	private final ProductRepository productRepository;
	private final CartService cartService;
	private final ModelMapper modelMapper;
	
		
	public OrderServiceImplementation(OrderRepository orderRepository, ProductRepository productRepository, CartService cartService, ModelMapper modelMapper) {
		super();
		this.orderRepository = orderRepository;
		this.productRepository = productRepository;
		this.cartService = cartService;
		this.modelMapper = modelMapper;
	}

	@Override
	public Order placeOrder(Long userId) {
		
		Cart cart = cartService.getCartByUserId(userId);
		
		Order order = createOrder(cart);
		
		List<OrderItems> orderItems =createOrderItems(order, cart);
	
		order.setOrderItems( new HashSet<>(orderItems));
		order.setTotalPrice(calcTotalPrice(orderItems));
		
		cartService.clearCart(cart.getId());
		
		return orderRepository.save(order);
	}
	
	
	private Order createOrder(Cart cart) {
		
		Order order = new Order();
		
		order.setUser(cart.getUser());
		order.setOrderStatus(OrderStatus.PENDING);
		order.setOrderDate( LocalDate.now());
		
		return order;
		
	}
	
	
	private List<OrderItems> createOrderItems(Order order , Cart cart){
		
		return cart.getCartItems().stream()
				                     .map(cartItem -> {
				                    	
				                    	 Product product = cartItem.getProduct();
				                    	 product.setInventory(product.getInventory() - cartItem.getQuantity());
				                    	 productRepository.save(product);
				                    	 
				                    	 return new OrderItems(
				                    			 
				                    			 order 
				                    			 ,product 
				                    			 , cartItem.getQuantity()
				                    			 , cartItem.getUnitPrice());
				                    	 
				                     }).toList();
		
	}
	
	
	
	private BigDecimal calcTotalPrice(List<OrderItems> items) {
		
		BigDecimal totalPrice = items.stream().map(item -> 
		                                            item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
				                                   .reduce(BigDecimal.ZERO , BigDecimal::add);
             return totalPrice;		
	}

	@Override
	public OrderDto getOrder(Long orderId) {
		
		return orderRepository.findById(orderId).map(this :: convertToDto)
				           .orElseThrow(() -> new RecourceNotFoundException("No Order found"));
	}
	
	@Override
	public List<OrderDto> getUserOrders(Long userId){
		
		return orderRepository.findAllByUserId(userId)
				                                  .stream()
				                                  .map(this :: convertToDto)
				                                  .toList();
	}
	
	@Override
	public OrderDto convertToDto(Order order) {
		
		List<OrderItemsDto> items = order.getOrderItems().stream()
				                                           .map(orderItem -> convertToOrderItemsDto(orderItem))
				                                           .toList();
		
		OrderDto orderDto =new OrderDto();
		
		orderDto.setOrderId(order.getOrderId());
		orderDto.setOrderDate(order.getOrderDate());
		orderDto.setTotalPrice(order.getTotalPrice());
		orderDto.setOrderStatus(order.getOrderStatus().toString());
		orderDto.setUserId(order.getUser().getId());
		orderDto.setOrderItems(items);
		
		return orderDto;
		
	}
	
	@Override
	public OrderItemsDto convertToOrderItemsDto(OrderItems orderItems) {
		
		OrderItemsDto dto = new OrderItemsDto();
		
		dto.setProductId(orderItems.getProduct().getId());
		dto.setProductName(orderItems.getProduct().getName());
		dto.setBrand(orderItems.getProduct().getBrand());
		dto.setQuantity(orderItems.getQuantity());
		dto.setUnitPrice(orderItems.getUnitPrice());
		
		return dto ;
	}
	
	
	
	

}
