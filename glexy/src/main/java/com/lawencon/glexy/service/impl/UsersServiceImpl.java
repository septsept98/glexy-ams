package com.lawencon.glexy.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lawencon.glexy.dao.TransactionDao;
import com.lawencon.glexy.dao.UsersDao;
import com.lawencon.glexy.email.EmailHandler;
import com.lawencon.glexy.exception.ValidationGlexyException;
import com.lawencon.glexy.helper.EmailHelper;
import com.lawencon.glexy.model.Company;
import com.lawencon.glexy.model.Employee;
import com.lawencon.glexy.model.File;
import com.lawencon.glexy.model.Roles;
import com.lawencon.glexy.model.Transactions;
import com.lawencon.glexy.model.Users;
import com.lawencon.glexy.service.CompanyService;
import com.lawencon.glexy.service.EmployeeService;
import com.lawencon.glexy.service.FileService;
import com.lawencon.glexy.service.RolesService;
import com.lawencon.glexy.service.UsersService;

@Service
public class UsersServiceImpl extends BaseGlexyServiceImpl implements UsersService {

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

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private EmailHandler emailHandler;

	@Autowired
	private TransactionDao transactionDao;

	@Override
	public List<Users> findAll() throws Exception {
		EmailHelper data = new EmailHelper();
		data.setEmployeeName("septian");
		data.setValueName("lenovo");
		data.setExpiredDate(LocalDate.now());
		emailHandler.sendSimpleMessage("glenn9828@gmail.com", "Expired Asset Reminder", "Close To Expired", data);
		return usersDao.findAll();
	}

	@Override
	public Users findById(String id) throws Exception {

		return usersDao.findById(id);
	}

	@Override
	public Users save(Users data, MultipartFile file) throws Exception {
		try {
			validationSave(data);
			String pass = generatePassword();
			System.out.println(pass);
			data.setPass(bCryptPasswordEncoder.encode(pass));
			data.setCreatedBy(getIdAuth());
			Roles roles = rolesService.findById(data.getRolesId().getId());
			data.setRolesId(roles);
			begin();

			Employee employee = employeeService.saveOrUpdate(data.getEmployeeId());

			if (employee == null) {
				throw new ValidationGlexyException("Employee Not Found");
			}

			Company company = companyService.findById(data.getEmployeeId().getCompanyId().getId());
			if (company == null) {
				throw new ValidationGlexyException("Company Not Found");
			}
			employee.setCompanyId(company);

			File files = new File();
			files.setFiles(file.getBytes());
			String ext = file.getOriginalFilename();
			ext = ext.substring(ext.lastIndexOf(".") + 1, ext.length());
			files.setExtension(ext);
			files = fileService.saveOrUpdate(files);
			files.setId(files.getId());
			files.setCreatedBy(data.getCreatedBy());
			data.setUsersImg(files);

			data.setEmployeeId(employee);
			Users user = usersDao.saveOrUpdate(data);
			commit();
			EmailHelper email = new EmailHelper();
			email.setValueName(pass);
			emailHandler.sendSimpleMessage(data.getEmail(), "Password ini rahasia", "Password", email);
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
			validationUpdate(data);
			Users users = usersDao.findById(data.getId());
			users.setUpdatedBy("1");
			users.setVersion(data.getVersion());

			Roles roles = rolesService.findById(data.getRolesId().getId());
			users.setRolesId(roles);
			begin();
			Employee employee = employeeService.saveOrUpdate(data.getEmployeeId());
			if (employee == null) {
				throw new ValidationGlexyException("Employee Not Found");
			}
			Company company = companyService.findById(data.getEmployeeId().getCompanyId().getId());
			if (company == null) {
				throw new ValidationGlexyException("Company Not Found");
			}
			employee.setCompanyId(company);

			if (file != null) {
				File files = new File();
				files.setFiles(file.getBytes());
				String ext = file.getOriginalFilename();
				ext = ext.substring(ext.lastIndexOf(".") + 1, ext.length());
				files.setExtension(ext);
				files.setId(users.getUsersImg().getId());
				files.setUpdatedBy(getIdAuth());

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
		String number = "123456789";
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 5; i++) {
			int indNumber = random.nextInt(number.length());
			sb.append(number.charAt(indNumber));
		}
		return sb.toString();
	}

	@Override
	public Users getByNip(String Nip) throws Exception {

		return usersDao.getByNip(Nip);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Users users = new Users();
		try {
			users = usersDao.getEmail(email);
		} catch (Exception e) {
			throw new UsernameNotFoundException(e.getMessage());
		}
		return new org.springframework.security.core.userdetails.User(users.getEmail(), users.getPass(),
				new ArrayList<>());
	}

	@Override
	public Users updatePassword(Users data) throws Exception {

		try {
			Users users = usersDao.findById(data.getId());
			users.setPass(bCryptPasswordEncoder.encode(data.getPass()));
			users.setUpdatedBy(getIdAuth());
			users.setVersion(data.getVersion());
			begin();
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
	public List<Users> findByRolesId(String id) throws Exception {

		return usersDao.findByRolesId(id);
	}

	@Override
	public void validationFk(String id) throws Exception {
		List<Transactions> dataTransactions = transactionDao.findByUsersId(id);
		if (dataTransactions.size() != 0) {

			throw new ValidationGlexyException("User in Use");
		}

	}

	@Override
	public void validationSave(Users data) throws Exception {
		if (data != null) {
			if (data.getEmail().isBlank()  || data.getRolesId() == null || data.getIsActive() == null) {
				throw new ValidationGlexyException("Data not Complete");
			}
		} else {
			throw new ValidationGlexyException("Data Empty");
		}
	}

	@Override
	public void validationUpdate(Users data) throws Exception {
		if (data != null) {
			if (data.getId() != null) {
				Users user = findById(data.getId());
				if (user == null) {
					throw new ValidationGlexyException("Data not Found");
				}
			} else {
				throw new ValidationGlexyException("Data not Found");
			}
			if (data.getEmail().isBlank()  || data.getRolesId() == null || data.getIsActive() == null) {
				throw new ValidationGlexyException("Data not Complete");
			}
		} else {
			throw new ValidationGlexyException("Data Empty");
		}
	}

	@Override
	public Users findByIdAuth() throws Exception {

		return usersDao.findById(getIdAuth());
	}

}
