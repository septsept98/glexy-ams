package com.lawencon.glexy.dao;

import java.util.List;

import com.lawencon.glexy.model.Permissions;

public interface PermissionsDao {
	
	List<Permissions> findAll() throws Exception; 

	Permissions findById(String id) throws Exception; 
	
	Permissions saveOrUpdate(Permissions permissions) throws Exception;
	
	boolean deleteById(String id) throws Exception;

}
