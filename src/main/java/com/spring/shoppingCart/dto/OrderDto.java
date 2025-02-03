package com.spring.shoppingCart.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


public class OrderDto {

	
			private Long orderId;
			
			private Long userId;
		
			private LocalDate orderDate;
			
			private BigDecimal totalPrice;
				
			private String orderStatus;
			
			private List<OrderItemsDto> orderItems;
	

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public List<OrderItemsDto> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItemsDto> orderItems) {
		this.orderItems = orderItems;
	}
	
	
	
	
	
}
