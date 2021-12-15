package com.lawencon.glexy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.glexy.dao.EmployeeDao;
import com.lawencon.glexy.model.Company;
import com.lawencon.glexy.model.Employee;

@Service
public class EmployeeServiceImpl extends BaseServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private CompanyService companyService;
	
	@Override
	public List<Employee> findAll() throws Exception {
		
		return employeeDao.findAll();
	}

	@Override
	public Employee findById(String id) throws Exception {
	
		return employeeDao.findById(id);
	}

	@Override
	public Employee saveOrUpdate(Employee data) throws Exception {
		try {
			if (data.getId() != null) {
				Employee employee = findById(data.getId());
				data.setNip(employee.getNip()); 
				data.setCreatedAt(employee.getCreatedAt());
				data.setCreatedBy(employee.getCreatedBy());
				data.setVersion(employee.getVersion());
				data.setIsActive(employee.getIsActive());
				
			}
			
			Company company = companyService.findById(data.getCompanyId().getId());
			data.setCompanyId(company);
			
			
			data = employeeDao.saveOrUpdate(data);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
		}
		return data;
	}

	@Override
	public boolean deleteById(String id) throws Exception {
		boolean result = false;
		try {
			begin();
			result = employeeDao.deleteById(id);
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new Exception(e);
		}
		return result;
	}

	
	
}
