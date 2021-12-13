package glexy.dao;

import java.util.List;

import glexy.model.Employee;



public interface EmployeeDao {
	
	List<Employee> findAll() throws Exception; 

	Employee findById(String id) throws Exception; 
	
	Employee saveOrUpdate(Employee employee) throws Exception;
	
	boolean deleteById(String id) throws Exception;
	
}
