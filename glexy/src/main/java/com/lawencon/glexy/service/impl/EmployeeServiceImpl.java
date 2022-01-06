package com.lawencon.glexy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.glexy.dao.EmployeeDao;
import com.lawencon.glexy.dao.TransactionDao;
import com.lawencon.glexy.dao.UsersDao;
import com.lawencon.glexy.exception.ValidationGlexyException;
import com.lawencon.glexy.model.Company;
import com.lawencon.glexy.model.Employee;
import com.lawencon.glexy.model.Transactions;
import com.lawencon.glexy.model.Users;
import com.lawencon.glexy.service.CompanyService;
import com.lawencon.glexy.service.EmployeeService;

@Service
public class EmployeeServiceImpl extends BaseGlexyServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeDao employeeDao;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private UsersDao usersDao;

	@Autowired
	private TransactionDao transactionDao;

	@Override
	public List<Employee> findAll() throws Exception {

		return employeeDao.findAllEmployees();
	}

	@Override
	public Employee findById(String id) throws Exception {

		return employeeDao.findById(id);
	}

	@Override
	public Employee saveOrUpdate(Employee data) throws Exception {
		try {
			if (data.getId() != null) {
				validationUpdate(data);
				Employee employee = findById(data.getId());
				data.setNip(employee.getNip());
				data.setUpdatedBy("1");
				data.setCreatedAt(employee.getCreatedAt());
				data.setCreatedBy(employee.getCreatedBy());
				data.setVersion(employee.getVersion());
				data.setIsActive(employee.getIsActive());

			} else {
				validationSave(data);
				data.setCreatedBy("2");

			}

			Company company = companyService.findById(data.getCompanyId().getId());
			if (company == null) {
				throw new ValidationGlexyException("Company Not Found");
			}
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
			validationFk(id);
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

	@Override
	public void validationFk(String id) throws Exception {

		List<Users> dataUsers = usersDao.findByEmployeeId(id);
		List<Transactions> dataTransactions = transactionDao.findByEmployeeId(id);
		if (dataUsers.size() != 0 || dataTransactions.size() != 0) {

			throw new ValidationGlexyException("Employee in Use");
		}
	}

	@Override
	public void validationSave(Employee data) throws Exception {
		if (data != null) {
			if (data.getGender().isBlank()  || data.getNameEmployee().isBlank()  || data.getNip().isBlank() 
					|| data.getPhoneNumber().isBlank() ) {
				throw new ValidationGlexyException("Data not Complete");
			}
		} else {
			throw new ValidationGlexyException("Data Empty");
		}
	}

	@Override
	public void validationUpdate(Employee data) throws Exception {
		if (data != null) {
			if (data.getId() != null) {
				Employee employee = findById(data.getId());
				if (employee == null) {
					throw new ValidationGlexyException("Data not Found");
				}
			} else {
				throw new ValidationGlexyException("Data not Found");
			}
			if (data.getGender().isBlank()  || data.getNameEmployee().isBlank()  || data.getNip().isBlank() 
					|| data.getPhoneNumber().isBlank() ) {
				throw new ValidationGlexyException("Data not Complete");
			}
		} else {
			throw new ValidationGlexyException("Data Empty");
		}
	}

	@Override
	public List<Employee> search(String search) throws Exception {
		List<Employee> result = new ArrayList<>();
		if(search.isBlank()) {
			return result;
		} else {
			result = employeeDao.search(search);
			return result;
		}
  }
  
	public Employee saveOrUpdateEmployee(Employee data) throws Exception {
		try {
			if (data.getId() != null) {
				validationUpdate(data);
				Employee employee = findById(data.getId());
				data.setNip(employee.getNip());
				data.setUpdatedBy("1");
				data.setCreatedAt(employee.getCreatedAt());
				data.setCreatedBy(employee.getCreatedBy());
				data.setVersion(employee.getVersion());
				data.setIsActive(employee.getIsActive());

			} else {
				validationSave(data);
				data.setCreatedBy("1");

			}

			Company company = companyService.findById(data.getCompanyId().getId());
			if (company == null) {
				throw new ValidationGlexyException("Company Not Found");
			}
			data.setCompanyId(company);
			begin();
			data = employeeDao.saveOrUpdate(data);
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
		}
		return data;
	}

}
