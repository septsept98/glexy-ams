package com.lawencon.glexy.service;

import java.util.List;

import com.lawencon.glexy.model.Permissions;

public interface PermissionsService {
	
	List<Permissions> findAll() throws Exception; 

	Permissions findById(String id) throws Exception; 
	
	Permissions saveOrUpdate(Permissions data) throws Exception;
	
	boolean deleteById(String id) throws Exception;
	
	void validationFk(String id) throws Exception;
	
	void validationSave(Permissions data) throws Exception;

	void validationUpdate(Permissions data) throws Exception;
}
