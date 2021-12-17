package com.lawencon.glexy.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthPrincipalImpl implements AuthPrincipal {

	@Override
	public Authentication getAthentication() {
		
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
}
