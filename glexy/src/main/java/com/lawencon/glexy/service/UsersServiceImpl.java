package com.lawencon.glexy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.glexy.dao.UsersDao;
import com.lawencon.glexy.model.Company;
import com.lawencon.glexy.model.Employee;
import com.lawencon.glexy.model.File;
import com.lawencon.glexy.model.Roles;
import com.lawencon.glexy.model.Users;

@Service
public class UsersServiceImpl extends BaseServiceImpl implements UsersService {
	
	@Autowired
	private UsersDao usersDao;
	
	private FileService fileService;
	
	@Autowired
	private RolesService rolesService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private CompanyService companyService;
	
	@Override
	public List<Users> findAll() throws Exception {
		
		return usersDao.findAll();
	}

	@Override
	public Users findById(String id) throws Exception {
	
		return usersDao.findById(id);
	}

	@Override
	public Users saveOrUpdate(Users data) throws Exception {
		try {
			if(data.getId() != null) {
				Users users = usersDao.findById(data.getId());
				data.setId(users.getId());
				data.setEmail(users.getEmail());
				data.setCreatedAt(data.getCreatedAt());
				data.setCreatedBy(users.getCreatedBy());
				data.setIsActive(users.getIsActive());
				data.setVersion(users.getVersion());
				
				Roles roles = rolesService.findById(data.getRolesId().getId());
				data.setRolesId(roles);
				
				Employee employee = employeeService.findById(data.getEmployeeId().getId());
				employee.setNameEmployee(data.getEmployeeId().getNameEmployee());
				employee.setPhoneNumber(data.getEmployeeId().getPhoneNumber());
				employee.setGender(data.getEmployeeId().getGender());
				
				Company company = companyService.findById(data.getEmployeeId().getCompanyId().getId());
				employee.setCompanyId(company);
				
				if(data.getUsersImg() == null) {
					
					data.setUsersImg(users.getUsersImg());
					
				}else {
					
					File file = fileService.saveOrUpdate(data.getUsersImg());
					data.setUsersImg(file);
				}
				
				data.setEmployeeId(employee);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
		}
		return usersDao.saveOrUpdate(data);
	}

	@Override
	public boolean deleteById(String id) throws Exception {
		
		boolean result = false;
		try {
			begin();
			result = usersDao.deleteById(id);
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new Exception(e);
		}
		return result;
	}

	@Override
	public Users getEmail(String email) throws Exception {
		
		return usersDao.getEmail(email);
	}	
	
}
