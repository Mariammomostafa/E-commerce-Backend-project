package com.spring.shoppingCart.service.Category;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.spring.shoppingCart.exceptions.AlreadyExistsException;
import com.spring.shoppingCart.exceptions.RecourceNotFoundException;
import com.spring.shoppingCart.model.Category;
import com.spring.shoppingCart.repository.CategoryRepository;

@Service
public class CategoryServiceImplementation implements CategoryService{

	private final CategoryRepository categoryRepository;
	
	public CategoryServiceImplementation(CategoryRepository categoryRepository) {
		super();
		this.categoryRepository = categoryRepository;
	}

	@Override
	public Category getCategoryById(long id) {
		
		return categoryRepository.findById(id)
				.orElseThrow(() -> new RecourceNotFoundException("Category Not found !!"));
	}

	@Override
	public Category getCategoryByName(String name) {
		
		return categoryRepository.findByName(name);
	}

	@Override
	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}

	@Override
	public Category addCategory(Category category) {
		
		return Optional.of(category).filter(c -> !categoryRepository.existsByName(c.getName()))
				      .map(categoryRepository:: save)
				      .orElseThrow(() ->new AlreadyExistsException(category.getName() + " already Exists !!"));
	}

	@Override
	public Category updateCategory(Category category , long id) {
		
		return Optional.ofNullable(getCategoryById(id))
				            .map(oldCategory -> { 
				        	oldCategory.setName(category.getName());
				        	return categoryRepository.save(oldCategory);
				        	
				        }).orElseThrow(() ->new RecourceNotFoundException("Category Not found !!"));
	}

	@Override
	public void deleteCategoryById(long id) {
		
		categoryRepository.findById(id)
		                   .ifPresentOrElse(categoryRepository::delete, ()-> {
		                	   throw new RecourceNotFoundException("Category Not found !!");
		                   });
		
	}

}
