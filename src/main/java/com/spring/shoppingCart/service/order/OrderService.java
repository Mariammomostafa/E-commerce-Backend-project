package com.spring.shoppingCart.service.order;

import java.util.List;

import com.spring.shoppingCart.dto.OrderDto;
import com.spring.shoppingCart.dto.OrderItemsDto;
import com.spring.shoppingCart.model.Order;
import com.spring.shoppingCart.model.OrderItems;

public interface OrderService {
	
	Order placeOrder(Long userId);
	
     OrderDto getOrder(Long orderId);

	List<OrderDto> getUserOrders(Long userId);

	OrderDto convertToDto(Order order);

	OrderItemsDto convertToOrderItemsDto(OrderItems orderItems);
	
	
}
