package com.lawencon.glexy.dao;

import java.util.List;

import com.lawencon.glexy.model.Employee;



public interface EmployeeDao {
	
	List<Employee> findAll() throws Exception; 

	Employee findById(String id) throws Exception; 
	
	Employee saveOrUpdate(Employee employee) throws Exception;
	
	boolean deleteById(String id) throws Exception;
	
}
