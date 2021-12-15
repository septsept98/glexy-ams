package com.lawencon.glexy.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.lawencon.glexy.model.Users;

public interface UsersService {
	
	List<Users> findAll() throws Exception; 

	Users findById(String id) throws Exception; 
	
	Users save(Users data,MultipartFile file) throws Exception;
	
	Users update(Users data,MultipartFile file) throws Exception;
	
	boolean deleteById(String id) throws Exception;
	
	Users getEmail(String email) throws Exception;
	
	String generatePassword() throws Exception;

}
