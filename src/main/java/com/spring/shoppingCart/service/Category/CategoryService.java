package com.spring.shoppingCart.service.Category;

import java.util.List;

import com.spring.shoppingCart.model.Category;

public interface CategoryService {
	
	Category getCategoryById(long id);
	Category getCategoryByName(String name);
	
	List<Category> getAllCategories();
	
	Category addCategory(Category category);
	Category updateCategory(Category category , long id);
	
	void deleteCategoryById(long id);
	

	

}
