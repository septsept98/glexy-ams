package glexy.dao;

import java.util.List;

import glexy.model.Users;



public interface UsersDao {
	
	List<Users> findAll() throws Exception; 

	Users findById(String id) throws Exception; 
	
	Users saveOrUpdate(Users users) throws Exception;
	
	boolean deleteById(String id) throws Exception;
	
	Users getEmail(String email) throws Exception;

}
