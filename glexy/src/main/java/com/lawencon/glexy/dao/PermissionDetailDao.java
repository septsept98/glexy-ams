package com.lawencon.glexy.dao;

import java.util.List;

import com.lawencon.glexy.model.PermissionDetail;



public interface PermissionDetailDao {
	
	List<PermissionDetail> findAll() throws Exception; 

	PermissionDetail findById(String id) throws Exception; 
	
	PermissionDetail saveOrUpdate(PermissionDetail permissionDetail) throws Exception;
	
	boolean deleteById(String id) throws Exception;
	
	List<PermissionDetail> findByRoleId(String id)throws Exception;

}
