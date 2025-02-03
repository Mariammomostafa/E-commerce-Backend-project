package com.spring.shoppingCart.security.JWT;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.type.filter.AbstractClassTestingTypeFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.spring.shoppingCart.security.user.ShopUserDetails;
import com.spring.shoppingCart.security.user.ShopUserDetailsService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


@Component
public class JwtUtils {
	
	@Value("${auth.token.jwtSecret}")
	private String jwtSecret;
	
	//@Value("${auth.token.expirationDate}")
	//private String expirationTime;

	
	
	
	
	public String generateToken(UserDetails userDetails) {
		
			List<String> roles = userDetails.getAuthorities()
				                              .stream()
				                              .map(GrantedAuthority::getAuthority)
				                              .toList();
		
		Map<String, Object> claims= new HashMap<>();
		claims.put("username", userDetails.getUsername());
		claims.put("roles",roles);
						
		return Jwts.builder()
			            	.setClaims(claims)
				           .setSubject(userDetails.getUsername())
				           .setIssuedAt(new Date())
				           .setExpiration(new Date(System.currentTimeMillis()+ 60*60*24*1000 ))
				           .signWith(setSigningKey() , SignatureAlgorithm.HS256)
				           .compact();
		
	}
	
                                            	/******************************/

	private SecretKey setSigningKey() {
		byte[] keyBytes =Decoders.BASE64.decode(jwtSecret);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	
	
				                         /******************************/
					
				public boolean isTokenValid(String token , UserDetails userDetails) {
				
						    final  	String username = getUsernameFromToken(token);
							return (username.equals(userDetails.getUsername())  && ! isTokenExpired(token));
				}
	
	                                         /******************************/
	
	private <T> T extractClaim(String token , Function<Claims, T> claimResolver){
		
		 final  Claims claims= getAllClaims(token);
		return claimResolver.apply(claims);
	}
	
	                                                   /******************************/
	
       private  Claims getAllClaims(String token) {
		
				return Jwts.parserBuilder().setSigningKey(setSigningKey())
						.build().parseClaimsJws(token).getBody(); 
	}
			                                                  /******************************/
			
			public String getUsernameFromToken(String token) {
				
				return extractClaim(token, Claims::getSubject);
				
			}
			
			                                                    /******************************/
			
			public Date getExpirationDate(String token) {
				
				return extractClaim(token, Claims::getExpiration);
			}
			
			                                               /******************************/
			
			private boolean isTokenExpired(String token) {
				
				return getExpirationDate(token).before(new Date());
			}
			
		
			
			                                        /******************************/

			
			/*
			
			public List<String> getRolesFromToken(String token){
				
				Claims claims = extractClaims(token);
				List<String> roles = claims.get("roles" , List.class);
				
				return roles;
			}
			                    
			                                               
			
			public Map<String , Object> getcustomClaims(String token){
				
				       Map<String , Object> customClaims = new HashMap<>();
				
							Claims claims = extractClaims(token);
							List<String> roles = claims.get("roles" , List.class);
							String firstName = claims.get("username" , String.class);
							Long id = claims.get("id" , Long.class);
							
							customClaims.put("1", roles);
							customClaims.put("2", firstName);
							customClaims.put("3", id);
							
							return customClaims;
						}

*/
			
			
}
