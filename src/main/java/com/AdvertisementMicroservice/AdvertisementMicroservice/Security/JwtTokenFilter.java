package com.AdvertisementMicroservice.AdvertisementMicroservice.Security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;


@Component
public class JwtTokenFilter extends GenericFilterBean {

	
	 @Autowired
	 private JwtTokenProvider jwtTokenProvide;
	 
	 @Override
	    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
	            throws IOException, ServletException {

	        	String token=jwtTokenProvide.resolveMicroToken((HttpServletRequest) req);
	        	if(token!=null && jwtTokenProvide.validateMicroserviceToken(token))
	        	{
	        		Authentication auth=jwtTokenProvide.getAuthenticationForMicro(token);
	        		if (auth != null) {
		                SecurityContextHolder.getContext().setAuthentication(auth);
		               
		            }
	        		
	        	}
	        filterChain.doFilter(req, res);
	    }
	 
}
