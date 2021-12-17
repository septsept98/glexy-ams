package com.lawencon.glexy.service;

import java.util.List;

import com.lawencon.glexy.dto.roles.RolesInsertReqDto;
import com.lawencon.glexy.model.Roles;

public interface RolesService {
	
	List<Roles> findAll() throws Exception; 

	Roles findById(String id) throws Exception; 
	
	Roles saveOrUpdate(RolesInsertReqDto data) throws Exception;
	
	boolean deleteById(String id) throws Exception;

}
