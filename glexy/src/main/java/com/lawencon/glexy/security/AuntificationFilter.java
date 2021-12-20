package com.lawencon.glexy.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawencon.glexy.dto.users.LoginReqDto;
import com.lawencon.glexy.dto.users.LoginResDto;
import com.lawencon.glexy.model.Users;
import com.lawencon.glexy.security.jwt.JwtComponent;
import com.lawencon.glexy.service.UsersService;

public class AuntificationFilter extends UsernamePasswordAuthenticationFilter {
	
	
	private ObjectMapper objectMapper;
	AuthenticationManager auntificationFilter;
	JwtComponent jwtComponent;
	UsersService usersService;
	
	@Autowired
	public AuntificationFilter(AuthenticationManager auntificationFilter,ObjectMapper objectMapper, JwtComponent jwtComponent, UsersService usersService) {
		this.objectMapper = objectMapper;
		this.auntificationFilter = auntificationFilter;
		this.jwtComponent = jwtComponent;
		this.usersService = usersService;
	
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		LoginReqDto loginReqDto = new LoginReqDto();
		try {
			loginReqDto = objectMapper.readValue(request.getInputStream(), LoginReqDto.class);
			
		} catch (Exception e) {
			
		}
		
		return auntificationFilter.authenticate(new UsernamePasswordAuthenticationToken(loginReqDto.getEmail(), loginReqDto.getPass()));
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
			Users users = new Users();
			
			try {
				 users = usersService.getEmail(authResult.getName());
				 
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			String token = jwtComponent.generateToken(users.getId(), users.getEmail());
			
			LoginResDto loginResDto = new LoginResDto();
			loginResDto.setRolesCode(users.getRolesId().getCode());
			loginResDto.setToken(token);
			response.setContentType("application/Json");
			response.getWriter().append(objectMapper.writeValueAsString(loginResDto));
		
	}
	
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		
		Map<String, Object> mapError = new HashMap<>();
		mapError.put("msg", "Login Failed");
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.getWriter().append(objectMapper.writeValueAsString(mapError));
	}
}
