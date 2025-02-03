package com.spring.shoppingCart.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.shoppingCart.dto.ProductDto;
import com.spring.shoppingCart.exceptions.AlreadyExistsException;
import com.spring.shoppingCart.exceptions.ProductNotFoundException;
import com.spring.shoppingCart.exceptions.RecourceNotFoundException;
import com.spring.shoppingCart.model.Product;
import com.spring.shoppingCart.request.AddProductRequest;
import com.spring.shoppingCart.request.UpdateProductRequest;
import com.spring.shoppingCart.response.ApiResponse;
import com.spring.shoppingCart.service.product.ProductService;

@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
	
	private final ProductService productService;

	public ProductController(ProductService productService) {
		super();
		this.productService = productService;
	}
	
	
	@GetMapping("/all")
	public  ResponseEntity<ApiResponse> getAllProducts(){
		
		try {
			List<Product> products= productService.getAllProducts();
			List<ProductDto> convertedProducts= productService.getConvertedDtos(products);
			
			 return  ResponseEntity.ok(new ApiResponse("Found !" , convertedProducts));
			 
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					          .body(new ApiResponse("Error  !" , e.getMessage()));
		}			
	}
	
	
	@GetMapping("/product/{id}/getById")
	public  ResponseEntity<ApiResponse> getProductById(@PathVariable long id){
		
		try {
			Product product = productService.getProductById(id);
			
			ProductDto productDto = productService.convertToDto(product);
			
			System.out.println(productDto.getImages().get(0).getFileName());
			System.out.println(productDto.getImages().get(0).getDownloadUrl());
			
			 return  ResponseEntity.ok(new ApiResponse("Found !" , productDto));
			 
		}catch (ProductNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
			          .body(new ApiResponse(e.getMessage() , null ));
		}
	}
	
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/add")
	public  ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product){
		try {
		
		Product theProduct = productService.addProduct(product);
		return ResponseEntity.ok(new ApiResponse("Added Successfully ..", theProduct));
		
		} catch (AlreadyExistsException e) {
			
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/{id}/update")
	public ResponseEntity<ApiResponse> updateCategory(@PathVariable long Id, @RequestBody UpdateProductRequest request){
		
		try {
		Product theProduct = productService.updateProduct(request, Id);
		
		ProductDto productDto = productService.convertToDto(theProduct);
		return ResponseEntity.ok(new ApiResponse("Updated Successfully ... !!", productDto));
		
		} catch (RecourceNotFoundException e) {
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
			          .body(new ApiResponse(e.getMessage() , null ));
		}	
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/{id}/delete")
	public  ResponseEntity<ApiResponse> deleteById(@PathVariable long id){
		
			try {
				
			productService.deleteProduct(id);
			return ResponseEntity.ok(new ApiResponse("Deleted !!", null));
			
				} catch (ProductNotFoundException e) {
				
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
				          .body(new ApiResponse(e.getMessage() , null ));
			 }
	}
	
	
	@GetMapping("/getByBrandAndName")
	public  ResponseEntity<ApiResponse> getProductsByBrandAndName(@RequestParam String brand , @RequestParam String name  ){
		 
		try {
			List<Product> products = productService.getProductsByBrandAndName(brand, name);
			
			if(products.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
				          .body(new ApiResponse("No Products Found" , null ));
			}
			
			List<ProductDto> convertedProducts= productService.getConvertedDtos(products);
			return ResponseEntity.ok(new ApiResponse("Found  !!", convertedProducts));
			
		} catch (Exception e) {
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
			          .body(new ApiResponse(e.getMessage() , null ));
		}
		
	}
	
	
	
	@GetMapping("/{category}/{brand}/getByCategoryAndBrand")
	public  ResponseEntity<ApiResponse> getProductsByCategoryAndBrand(@PathVariable String category , @PathVariable String brand  ){
		 
		try {
			List<Product> products = productService.getProductsByCategoryAndBrand(category, brand);
			
			if(products.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
				          .body(new ApiResponse("No Products Found" , null ));
			}
			
			List<ProductDto> convertedProducts= productService.getConvertedDtos(products);
			return ResponseEntity.ok(new ApiResponse("Found  !!", convertedProducts));
			
		} catch (Exception e) {
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
			          .body(new ApiResponse(e.getMessage() , null ));
		}
	}
	
	
	@GetMapping("/{name}/getByName")
	public  ResponseEntity<ApiResponse> getProductsByName(@PathVariable String name ){
		 
		try {
			List<Product> products = productService.getProductsByName(name);
			
			if(products.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
				          .body(new ApiResponse("No Products Found" , null ));
			}
			
			List<ProductDto> convertedProducts= productService.getConvertedDtos(products);
			return ResponseEntity.ok(new ApiResponse("Found  !!", convertedProducts));
			
		} catch (Exception e) {
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
			          .body(new ApiResponse(e.getMessage() , null ));
		}
	}
	
	
	@GetMapping("/{brand}/getByBrand")
	public  ResponseEntity<ApiResponse> getProductsByBrand(@PathVariable String brand ){
		 
		try {
			List<Product> products = productService.getProductsByBrand(brand);
			
			if(products.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
				          .body(new ApiResponse("No Product Found" , null ));
			}
			
			List<ProductDto> convertedProducts= productService.getConvertedDtos(products);
			return ResponseEntity.ok(new ApiResponse("Found  !!", convertedProducts));
			
		} catch (Exception e) {
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
			          .body(new ApiResponse(e.getMessage() , null ));
		}
	}
	
	
	@GetMapping("/getByCategory")
	public  ResponseEntity<ApiResponse> getProductsByCaregory(@RequestParam String category ){
		 
		try {
			List<Product> products = productService.getProductsByCategory(category);
			
			if(products.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
				          .body(new ApiResponse("No Products Found" , null ));
			}
			
			List<ProductDto> convertedProducts= productService.getConvertedDtos(products);
			return ResponseEntity.ok(new ApiResponse("Found  !!", convertedProducts));
			
		} catch (Exception e) {
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
			          .body(new ApiResponse(e.getMessage() , null ));
		}
	}
	
	
	@GetMapping("/count/byBrandAndName")
	public  ResponseEntity<ApiResponse> countProductsByBrandAndName(@RequestParam String brand , @RequestParam String name  ){
		 
		try {
			var productCount = productService.countProductsByBrandAndName(brand, name);
			return ResponseEntity.ok(new ApiResponse("Found  !!", productCount));
			
		} catch (Exception e) {
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
			          .body(new ApiResponse(e.getMessage() , null ));
		}
		
	}
	
	
	

}
