package com.spring.shoppingCart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.shoppingCart.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

	List<Product>  findByCategoryName(String categoryName);

	List<Product>  findByBrand(String brandName);


	List<Product> findByCategoryNameAndBrand(String categoryName, String brandName);

	List<Product>  findByName(String name);

	List<Product>   findByBrandAndName(String brandName, String name);

	Long   countByBrandAndName(String brandName, String name);

	boolean existsByNameAndBrand(String name, String brand);

}
