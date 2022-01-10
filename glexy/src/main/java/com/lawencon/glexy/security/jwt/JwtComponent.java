package com.lawencon.glexy.security.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtComponent {

	String secretKey = "12345123451234512345123456201111111111109319999999999999999999931811111111111193333333339193183191313138131138";
	SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

	public String generateToken(String userId, String email) {
		Map<String, Object> claims = new HashMap<String, Object>();

		claims.put("userId", userId);
		claims.put("username", email);
		String token = Jwts.builder().signWith(key).setClaims(claims)
				.setExpiration(new Date(new Date().getTime() + 14400000)).compact();

		return token;
	}

	public Claims parseClaim(String token) throws Exception {

		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();

	}

}
