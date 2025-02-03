# E-commerce API System

It is a secured e-commerce back-end system that include many transactions through wich
admin and customer can apply their missions eaisly 

## Features
### security
This project has Authentication and autherization system handled by Spring Security
- User get JWT token after successful login which will be needed later in each request
 - Access to secured end-points depends on the user's role (    USER(customer) , ADMIN) and verification of the JWT token with this request.

### Admin control
- admin can add main Categories of the system , update and deleting them, add productes for each category , upload images for each product , get user whether the user has cart or not

### User  
- After successful registeration and login , user can view all Categories , all products of each category , download images for each product , add what he want from items to it's own cart, remove items , update quantities , get total price, make order for only one cart then clear the cart to make another order , get specific order and get all orders
### Prerequisites
- Java 17
- Spring Boot 3.4.1
- spring security 6
- Maven
- Postman
- IDE (Eclips)


