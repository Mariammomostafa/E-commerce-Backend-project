package com.spring.shoppingCart.controller;

import java.util.List;

import  org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.shoppingCart.exceptions.AlreadyExistsException;
import com.spring.shoppingCart.exceptions.RecourceNotFoundException;
import com.spring.shoppingCart.model.Category;
import com.spring.shoppingCart.response.ApiResponse;
import com.spring.shoppingCart.service.Category.CategoryService;

@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
	
   private final CategoryService categoryService;

public CategoryController(CategoryService categoryService) {
	super();
	this.categoryService = categoryService;
}

		@GetMapping("/all")
		public  ResponseEntity<ApiResponse> getAllCategories(){
			
			 try {
				List<Category> categories= categoryService.getAllCategories();
				 
				 return  ResponseEntity.ok(new ApiResponse("Found !" , categories));
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						          .body(new ApiResponse("Error  !" , e.getMessage()));
			}
		}


		@PostMapping("/add")
		public  ResponseEntity<ApiResponse> addCategory(@RequestBody Category cat){
			
			try {
				Category category = categoryService.addCategory(cat);
				return ResponseEntity.ok(new ApiResponse("Added Successfully ..", category));
			} catch (AlreadyExistsException e) {
				
				return ResponseEntity.status(HttpStatus.CONFLICT)
						.body(new ApiResponse(e.getMessage(), null));
			}
		}
		
		@GetMapping("/category/{id}/getById")
		public  ResponseEntity<ApiResponse> getCategoryById(@PathVariable long id){
			
			try {
				Category category = categoryService.getCategoryById(id);
				return ResponseEntity.ok(new ApiResponse("Find !!", category));
			} catch (RecourceNotFoundException e) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
				          .body(new ApiResponse(e.getMessage() , null ));
			}
		}
		
		
		@GetMapping("/category/{name}/getByName")
		public  ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name){
			
			try {
				
				Category category = categoryService.getCategoryByName(name);
				return ResponseEntity.ok(new ApiResponse("Find !!", category));
				
			} catch (RecourceNotFoundException e) {
				
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
				          .body(new ApiResponse(e.getMessage() , null ));
			}
		}
		
		
		@DeleteMapping("/category/{id}/delete")
		public  ResponseEntity<ApiResponse> deleteById(@PathVariable long id){
			
			try {
				 categoryService.deleteCategoryById(id);
				return ResponseEntity.ok(new ApiResponse("Deleted !!", null));
			} catch (RecourceNotFoundException e) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
				          .body(new ApiResponse(e.getMessage() , null ));
			}
		}
		
		
		@PutMapping("/category/{id}/update")
		public ResponseEntity<ApiResponse> updateCategory(@PathVariable long Id, @RequestBody Category cate){
			
			try {
				Category category = categoryService.updateCategory(cate, Id);
				return ResponseEntity.ok(new ApiResponse("Updated Successfully ... !!", category));
			} catch (RecourceNotFoundException e) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
				          .body(new ApiResponse(e.getMessage() , null ));
			}
		}
		
		

}
