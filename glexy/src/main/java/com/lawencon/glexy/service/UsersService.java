package com.lawencon.glexy.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import com.lawencon.glexy.model.Users;

public interface UsersService extends UserDetailsService {
	
	List<Users> findAll() throws Exception; 

	Users findById(String id) throws Exception; 
	
	Users save(Users data,MultipartFile file) throws Exception;
	
	Users update(Users data,MultipartFile file) throws Exception;
	
	boolean deleteById(String id) throws Exception;
	
	Users getEmail(String email) throws Exception;
	
	String generatePassword() throws Exception;
	
	Users getByNip(String Nip) throws Exception;
	
	Users updatePassword(Users users) throws Exception;
	
	List<Users> findByRolesId(String id) throws Exception;
	
	void validationFk(String id) throws Exception;
	
	void validationSave(Users data) throws Exception;

	void validationUpdate(Users data) throws Exception;
	
	Users findByIdAuth() throws Exception; 
	
	Users resetPassword(String id) throws Exception;
}
