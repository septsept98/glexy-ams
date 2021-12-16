package com.lawencon.glexy.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.BaseDaoImpl;
import com.lawencon.glexy.dao.EmployeeDao;
import com.lawencon.glexy.model.Employee;

@Repository
public class EmployeeDaoImpl extends BaseDaoImpl<Employee> implements EmployeeDao {

	@Override
	public List<Employee> findAll() throws Exception {
		return getAll();
	}

	@Override
	public Employee findById(String id) throws Exception {
		return getById(id);
	}

	@Override
	public Employee saveOrUpdate(Employee employee) throws Exception {
		return save(employee);
	}

	@Override
	public boolean deleteById(String id) throws Exception {	
		
		return super.deleteById(id);
	}


}
