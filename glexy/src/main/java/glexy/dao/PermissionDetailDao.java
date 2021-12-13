package glexy.dao;

import java.util.List;

import glexy.model.PermissionDetail;



public interface PermissionDetailDao {
	
	List<PermissionDetail> findAll() throws Exception; 

	PermissionDetail findById(String id) throws Exception; 
	
	PermissionDetail saveOrUpdate(PermissionDetail permissionDetail) throws Exception;
	
	boolean deleteById(String id) throws Exception;

}
