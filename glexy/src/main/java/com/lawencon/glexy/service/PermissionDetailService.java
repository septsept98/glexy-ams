package com.lawencon.glexy.service;

import java.util.List;

import com.lawencon.glexy.model.PermissionDetail;

public interface PermissionDetailService {

	List<PermissionDetail> findAll() throws Exception; 

	PermissionDetail findById(String id) throws Exception; 
	
	PermissionDetail saveOrUpdate(PermissionDetail data) throws Exception;
	
	boolean deleteById(String id) throws Exception;
	
	List<PermissionDetail> findByRoleId(String id)throws Exception;
	
}
