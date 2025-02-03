package com.spring.shoppingCart.security.JWT;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.spring.shoppingCart.security.user.ShopUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	private  JwtUtils jwtUtils ;
	
	private  ShopUserDetailsService shopUserDetailsService;
	
	public JwtAuthenticationFilter(JwtUtils jwtUtils, ShopUserDetailsService shopUserDetailsService) {
		super();
		this.jwtUtils = jwtUtils;
		this.shopUserDetailsService = shopUserDetailsService;
	}


	@Override
	protected void doFilterInternal(HttpServletRequest request,
			                                               HttpServletResponse response,
			                                               FilterChain filterChain)  throws ServletException, IOException {
		
		        String header =request.getHeader("Authorization");
				
				if(StringUtils.isEmpty(header) || !StringUtils.startsWith(header, "Bearer ") ) {
					filterChain.doFilter(request, response);
					return;
				}
		
		String token = header.substring("Bearer ".length());
		String email = jwtUtils.getUsernameFromToken(token);
		
		if(email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			
		         UserDetails userDetails = shopUserDetailsService.loadUserByUsername(email);
			
		            if(jwtUtils.isTokenValid(token, userDetails)) {
		            	
		            	UsernamePasswordAuthenticationToken authenticationToken =
		            			                       new UsernamePasswordAuthenticationToken(userDetails , null , userDetails.getAuthorities());
		            	
		            	authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		            	SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		     }
		}
		
		filterChain.doFilter(request, response);
	}
}
