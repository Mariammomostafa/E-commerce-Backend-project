package com.spring.shoppingCart.dto;

import java.math.BigDecimal;


public class CartItemDto {
	
	
	private Long id;
	
	private int quantity;
	
	private BigDecimal unitPrice;
	
	private ProductDto product;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public ProductDto getProductDto() {
		return product;
	}

	public void setProductDto(ProductDto productDto) {
		this.product = productDto;
	}
	
	

}
