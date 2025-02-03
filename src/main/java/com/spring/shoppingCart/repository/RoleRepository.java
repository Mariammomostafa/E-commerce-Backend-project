package com.spring.shoppingCart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.shoppingCart.model.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	    Optional<Role> findByName(String role);
}
