package com.spring.shoppingCart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.shoppingCart.model.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long>{

	List<Image> findByProductId(long id);

}
