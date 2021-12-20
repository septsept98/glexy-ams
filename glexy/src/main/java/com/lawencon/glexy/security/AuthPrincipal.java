package com.lawencon.glexy.security;

import org.springframework.security.core.Authentication;

public interface AuthPrincipal {

	Authentication getAthentication();
}
