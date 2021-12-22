package com.lawencon.glexy.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.BaseDaoImpl;
import com.lawencon.glexy.dao.EmployeeDao;
import com.lawencon.glexy.model.Company;
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

	@Override
	public List<Employee> findByCompanyId(String id) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT company_id FROM employee ");
		sql.append("WHERE company_id = :id");
		List<?> result = createNativeQuery(sql.toString()).setParameter("id", id).getResultList();

		List<Employee> resultEmployee = new ArrayList<>();

		result.forEach(rs -> {

			Employee data = new Employee();

			Company company = new Company();
			company.setId(rs.toString());
			data.setCompanyId(company);
			resultEmployee.add(data);
		});

		return resultEmployee;
	}


}
