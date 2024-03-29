package com.example.demo.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.error.custom.ObjectNotFoundException;
import com.example.demo.security.service.UserDetailServiceImpl;

@Component
//this class only called if the request is must authorized request
public class JwtAuthTokenFilter extends OncePerRequestFilter {

	private static final String MY_ROLE = "sol_role";
	
	@Autowired
	private JwtProvider tokenProvider;
	
	@Autowired
	private UserDetailServiceImpl userDetailService;
	
	private static final Logger logger = LoggerFactory.getLogger(JwtAuthTokenFilter.class);
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		try {
			
			String jwt = getJwt(request);
			if(jwt != null && tokenProvider.validateJwtToken(jwt)) {
				String username = tokenProvider.getUsernameFromJwtToken(jwt);
				
				UserDetails userDetails = userDetailService.loadUserByUsernameAndRole(username, isAdmin(request));
				UsernamePasswordAuthenticationToken authentication	
					= new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authentication);
		
			}
			
		} catch (Exception ex) {
			logger.error("Can NOT set user authentication", ex);
		}
		
		filterChain.doFilter(request, response);
		
	}

	private String getJwt(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		
		if(authHeader != null && authHeader.startsWith("Bearer")) {
			return authHeader.replace("Bearer ", "");
		}
		
		return authHeader;
	}
	
	
	private boolean isAdmin(HttpServletRequest request) {
		String myRole = request.getHeader(MY_ROLE);
		
		if (myRole != null) {
			if (!myRole.equals("UPLOADER") && !myRole.equals("USER")) {
				throw new ObjectNotFoundException("Can not define your NTE0KW: "+myRole);
			} else {
				if(myRole.equals("UPLOADER")) {
					return true;
				} else {
					return false;
				}
			}
		}
		
		return false;
	}
}
