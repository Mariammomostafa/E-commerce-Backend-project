package com.spring.shoppingCart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.shoppingCart.model.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long>{

	void deleteAllByCartId(long id);

}
