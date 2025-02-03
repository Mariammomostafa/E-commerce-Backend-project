package com.spring.shoppingCart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.shoppingCart.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

	List<Order> findAllByUserId(Long userId);

}
