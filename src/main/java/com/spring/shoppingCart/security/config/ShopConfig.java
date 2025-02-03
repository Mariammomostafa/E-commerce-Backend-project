package com.spring.shoppingCart.security.config;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.spring.shoppingCart.security.JWT.JwtAuthenticationFilter;
import com.spring.shoppingCart.security.user.ShopUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class ShopConfig { 
	
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final ShopUserDetailsService userDetailsService;
	
	private static final List<String> SECURED_URLS =
			                                 List.of("/api/v1/cartItems/**"
			                                		 
			                                		  , "/api/v1/carts/**");
	
	private static final List<String> NON_SECURED_URLS =
												            List.of(
												            		"/api/v1/products/**",
												            		"api/v1/**"							            		
												            		
												            		);
	
	public ShopConfig(JwtAuthenticationFilter jwtAuthenticationFilter, ShopUserDetailsService userDetailsService) {
		super();
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
		this.userDetailsService = userDetailsService;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http.csrf(csrf -> csrf.disable())
		                             .cors(Customizer.withDefaults())
		                            .authenticationProvider(authenticationProvider())
		                            .authorizeHttpRequests(auth ->auth
		                            		.requestMatchers(SECURED_URLS.toArray(String[]::new)).authenticated()
		                            		.requestMatchers(NON_SECURED_URLS.toArray(String[]::new)).permitAll()
		                            	    .anyRequest().authenticated())
		                            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		                            .authenticationProvider(authenticationProvider())
		            		        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		            		        
		           return    http.build();     
		}	

	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager manager(AuthenticationConfiguration config) throws Exception {
		
		return config.getAuthenticationManager();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(getPasswordEncoder());
		
		return provider; 
		}
	
	

}
