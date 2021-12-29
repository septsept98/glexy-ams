package com.lawencon.glexy.service;

import java.util.List;

import com.lawencon.glexy.model.Employee;

public interface EmployeeService {

	List<Employee> findAll() throws Exception; 

	Employee findById(String id) throws Exception; 
	
	Employee saveOrUpdate(Employee data) throws Exception;
	
	boolean deleteById(String id) throws Exception;
	
	void validationFk(String id) throws Exception;
	
	void validationSave(Employee data) throws Exception;

	void validationUpdate(Employee data) throws Exception;
	
	Employee saveOrUpdateEmployee(Employee data) throws Exception;
	
}
