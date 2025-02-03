package com.spring.shoppingCart.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.shoppingCart.dto.OrderDto;
import com.spring.shoppingCart.exceptions.RecourceNotFoundException;
import com.spring.shoppingCart.model.Order;
import com.spring.shoppingCart.response.ApiResponse;
import com.spring.shoppingCart.service.order.OrderService;

@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
	
	private final OrderService orderService;
		
	public OrderController(OrderService orderService) {
		super();
		this.orderService = orderService;
	}


	@PostMapping("/createOrder")
	public  ResponseEntity<ApiResponse> createOrder(@RequestParam  Long userId){
		
		try {
			
			Order order = orderService.placeOrder(userId);
			
			OrderDto orderDto = orderService.convertToDto(order);
			 return  ResponseEntity.ok(new ApiResponse("Order Created Successfully !" , orderDto));
			 
		} catch (Exception e) {
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse("Error Occured .." ,e.getMessage()));
		}		
	}
	
	@GetMapping("/getOrder/{userId}")
	public  ResponseEntity<ApiResponse> getOrderById(@PathVariable  Long orderId){
		
		try {
			
			OrderDto order = orderService.getOrder(orderId);
			 return  ResponseEntity.ok(new ApiResponse("Order Founded !" , order));
			 
		}catch (RecourceNotFoundException e) {
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse("Oops ! .." ,e.getMessage()));
		}	
	}
	
	
	
	@GetMapping("/getUserOrders/{userId}")
	public  ResponseEntity<ApiResponse> getUserOrders(@PathVariable  Long userId){
		
		try {
			
			List<OrderDto> orders = orderService.getUserOrders(userId);
			 return  ResponseEntity.ok(new ApiResponse("Order Founded !" , orders));
			 
		}catch (RecourceNotFoundException e) {
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse("Oops ! .." ,e.getMessage()));
		}	
	}
	

}
