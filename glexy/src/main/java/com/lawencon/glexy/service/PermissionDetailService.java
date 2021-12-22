package com.lawencon.glexy.service;

import java.util.List;

import com.lawencon.glexy.model.Location;
import com.lawencon.glexy.model.PermissionDetail;

public interface PermissionDetailService {

	List<PermissionDetail> findAll() throws Exception; 

	PermissionDetail findById(String id) throws Exception; 
	
	PermissionDetail saveOrUpdate(PermissionDetail data) throws Exception;
	
	boolean deleteById(String id) throws Exception;
	
	List<PermissionDetail> findByRoleId(String id)throws Exception;
	
	List<PermissionDetail> findByRoleCode(String code)throws Exception;
	
	List<PermissionDetail> findByPermissionsId(String id)throws Exception;
	
	void validationSave(PermissionDetail data) throws Exception;

	void validationUpdate(PermissionDetail data) throws Exception;
}
