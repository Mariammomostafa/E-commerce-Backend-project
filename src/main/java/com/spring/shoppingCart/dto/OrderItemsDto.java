package com.spring.shoppingCart.dto;

import java.math.BigDecimal;

public class OrderItemsDto {
	
		    private Long productId;
		    
		    private String productName;
		    
		    private String brand;
		    
			private int quantity;
			
			private BigDecimal unitPrice;

			

			public String getProductName() {
				return productName;
			}

			public void setProductName(String productName) {
				this.productName = productName;
			}

			public Long getProductId() {
				return productId;
			}

			public void setProductId(Long productId) {
				this.productId = productId;
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

			public String getBrand() {
				return brand;
			}

			public void setBrand(String brand) {
				this.brand = brand;
			}
			
			
			

}
