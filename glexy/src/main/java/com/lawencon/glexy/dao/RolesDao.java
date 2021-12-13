package com.lawencon.glexy.dao;


import java.util.List;

import com.lawencon.glexy.model.Roles;

public interface RolesDao {

	List<Roles> findAll() throws Exception; 

	Roles findById(String id) throws Exception; 
	
	Roles saveOrUpdate(Roles roles) throws Exception;
	
	boolean deleteById(String id) throws Exception;
	
}
