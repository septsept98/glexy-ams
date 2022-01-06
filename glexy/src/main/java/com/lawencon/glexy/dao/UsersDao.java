package com.lawencon.glexy.dao;

import java.util.List;

import com.lawencon.glexy.model.Users;



public interface UsersDao {
	
	List<Users> findAll() throws Exception; 

	Users findById(String id) throws Exception; 
	
	Users saveOrUpdate(Users users) throws Exception;
	
	boolean deleteById(String id) throws Exception;
	
	Users getEmail(String email) throws Exception;

	Users getByNip(String nip) throws Exception;
	
	List<Users> findByRolesId(String id)throws Exception;
	
	List<Users> findByEmployeeId(String id)throws Exception;
	
	List<Users> findAllUsers() throws Exception; 
}
