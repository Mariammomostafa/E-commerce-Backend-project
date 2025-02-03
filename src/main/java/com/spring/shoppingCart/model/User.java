package com.spring.shoppingCart.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.hibernate.annotations.NaturalId;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private String firstName;
	
	private String lastName;
	
	private String password;
	
	@NaturalId
	private String email;
	
	@OneToOne(mappedBy = "user" , cascade = CascadeType.ALL , orphanRemoval = true)
	private Cart cart;
	
	
	@OneToMany(mappedBy = "user" , cascade = CascadeType.ALL , orphanRemoval = true)
	private List<Order>  orders;
	
	@ManyToMany(fetch = FetchType.EAGER , cascade = {CascadeType.DETACH ,CascadeType.PERSIST ,CascadeType.REFRESH ,CascadeType.MERGE})
	@JoinTable(
			name = "user_role",
			joinColumns = @JoinColumn(name= "user_id" , referencedColumnName = "id") ,
			inverseJoinColumns = @JoinColumn (name ="role_id" , referencedColumnName = "id")
			
			
			)
	private Collection<Role>  roles =new HashSet<>();
 
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}
	
	

}
