package com.lawencon.glexy.service;

import java.util.List;

import com.lawencon.glexy.model.Users;

public interface UsersService {
	
	List<Users> findAll() throws Exception; 

	Users findById(String id) throws Exception; 
	
	Users saveOrUpdate(Users data) throws Exception;
	
	boolean deleteById(String id) throws Exception;
	
	Users getEmail(String email) throws Exception;

}
