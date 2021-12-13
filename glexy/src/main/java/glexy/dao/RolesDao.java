package glexy.dao;


import java.util.List;

import glexy.model.Roles;

public interface RolesDao {

	List<Roles> findAll() throws Exception; 

	Roles findById(String id) throws Exception; 
	
	Roles saveOrUpdate(Roles roles) throws Exception;
	
	boolean deleteById(String id) throws Exception;
	
}
