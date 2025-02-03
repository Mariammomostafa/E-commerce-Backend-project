package com.spring.shoppingCart.data;

import java.util.Set;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.spring.shoppingCart.model.Role;
import com.spring.shoppingCart.model.User;
import com.spring.shoppingCart.repository.RoleRepository;
import com.spring.shoppingCart.repository.UserRepository;

import jakarta.transaction.Transactional;

@Transactional
@Component
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent>{
	
	private final UserRepository userRepository;	
	private final RoleRepository roleRepository;	
	private final PasswordEncoder  passwordEncoder;
	
	public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
		super();
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		
		Set<String> defaultRoles =Set.of( "ROLE_ADMIN" , "ROLE_USER" );
		createDefualtRoleIfNotExists(defaultRoles);
	    createDefaultUserIfNotExists();
	    createDefaultAdminIfNotExists();
	}

	private void createDefaultUserIfNotExists() {
		
		Role userRole= roleRepository.findByName("ROLE_USER").get();

           for(int i =1 ; i<= 5 ; i++) {
        	   
        	   String defualtEmail = "user" +i+"@gamil.com";
        	   if(userRepository.existsByEmail(defualtEmail)) {
        		   continue;
        	   }
        	   
        	   User user = new User();
        	   user.setFirstName("The User");
        	   user.setLastName("user "+i);
        	   user.setEmail(defualtEmail);
        	   user.setRoles(Set.of(userRole));
        	   user.setPassword(passwordEncoder.encode("123456"));
        	   
        	   userRepository.save(user);
        	   
        	   System.out.println("User No : " +i+ "  Created Successfully !!");
        	   
           }
	}
	
	
	private void createDefaultAdminIfNotExists() {
		
		Role adminRole= roleRepository.findByName("ROLE_ADMIN").get();

        for(int i =1 ; i<= 2 ; i++) {
     	   
     	   String defualtEmail = "Admin" +i+"@gamil.com";
     	   if(userRepository.existsByEmail(defualtEmail)) {
     		   continue;
     	   }
     	   
     	   User user = new User();
     	   user.setFirstName("Admin");
     	   user.setLastName("Admin "+i);
     	   user.setEmail(defualtEmail);
     	  user.setRoles(Set.of(adminRole));
     	   user.setPassword(passwordEncoder.encode("123456"));
     	   
     	   userRepository.save(user);
     	   
     	   System.out.println("Admin No : " +i+ "  Created Successfully !!");
     	   
        }
	}
	
	
	
	private void createDefualtRoleIfNotExists(Set<String> roles) {
		roles.stream()
		            .filter(role -> roleRepository.findByName(role).isEmpty())
		            .map(Role::new).forEach(roleRepository::save);
	}

}
