package com.spring.shoppingCart.service.product;

import java.util.List;

import com.spring.shoppingCart.dto.ProductDto;
import com.spring.shoppingCart.model.Product;
import com.spring.shoppingCart.request.AddProductRequest;
import com.spring.shoppingCart.request.UpdateProductRequest;

public interface ProductService {

	Product addProduct(AddProductRequest request);
		
	Product getProductById(long id);
	
	void deleteProduct(long id);
	
	Product updateProduct(UpdateProductRequest request , long productId);
	
	List<Product> getAllProducts();
	List<Product> getProductsByCategory(String categoryName);
	List<Product> getProductsByBrand(String brandName);
	List<Product> getProductsByCategoryAndBrand(String brandName ,String categoryName);
	List<Product> getProductsByName(String name);
	List<Product> getProductsByBrandAndName(String brandName ,String name);

    Long countProductsByBrandAndName(String brandName ,String name);

	ProductDto convertToDto(Product product);

	List<ProductDto> getConvertedDtos(List<Product> products);

	

}
