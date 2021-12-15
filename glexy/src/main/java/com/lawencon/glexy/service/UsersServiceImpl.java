package com.lawencon.glexy.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

	@Autowired
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
	public Users save(Users data, MultipartFile file) throws Exception {
		try {

			String pass = generatePassword();
			data.setPass(pass);

			Roles roles = rolesService.findById(data.getRolesId().getId());
			data.setRolesId(roles);
			begin();
			Employee employee = employeeService.saveOrUpdate(data.getEmployeeId());

			Company company = companyService.findById(data.getEmployeeId().getCompanyId().getId());
			employee.setCompanyId(company);

			File files = new File();
			files.setFiles(file.getBytes());
			String ext = file.getOriginalFilename();
			ext = ext.substring(ext.lastIndexOf(".") + 1, ext.length());
			files.setExtension(ext);
			files = fileService.saveOrUpdate(files);

			data.setUsersImg(files);

			data.setEmployeeId(employee);
			Users user = usersDao.saveOrUpdate(data);
			commit();
			return user;

		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new Exception(e);
		}

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

	@Override
	public Users update(Users data, MultipartFile file) throws Exception {

		try {

			Users users = usersDao.findById(data.getId());

			Roles roles = rolesService.findById(data.getRolesId().getId());
			users.setRolesId(roles);
			begin();
			Employee employee = employeeService.saveOrUpdate(data.getEmployeeId());

			Company company = companyService.findById(data.getEmployeeId().getCompanyId().getId());
			employee.setCompanyId(company);

			if (file != null) {
				File files = new File();
				files.setFiles(file.getBytes());
				String ext = file.getOriginalFilename();
				ext = ext.substring(ext.lastIndexOf(".") + 1, ext.length());

				files.setExtension(ext);

				files.setId(users.getUsersImg().getId());

				files = fileService.saveOrUpdate(files);

				users.setUsersImg(files);

			}

			users.setEmployeeId(employee);
			Users user = usersDao.saveOrUpdate(users);
			commit();
			return user;

		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new Exception(e);
		}
	}

	@Override
	public String generatePassword() throws Exception {
		String alphabet = "ABCDEFGHIJKLMOPQRSTUVWYZ";
		String number = "123456789";
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 5; i++) {
			int indAlphabhet = random.nextInt(alphabet.length());
			int indNumber = random.nextInt(number.length());
			sb.append(alphabet.charAt(indAlphabhet));
			sb.append(number.charAt(indNumber));

		}
		return sb.toString();
	}

}
