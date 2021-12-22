package com.lawencon.glexy.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.lawencon.base.ConnHandler;
import com.lawencon.glexy.model.Company;
import com.lawencon.glexy.model.Employee;
import com.lawencon.glexy.model.Roles;
import com.lawencon.glexy.model.Users;

@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
@DataJpaTest 
class UsersDaoTest {
	
	@Autowired
	private UsersDao usersDao;
	
	@Autowired
	private RolesDao rolesDao;
	
	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private EmployeeDao employeeDao;
	
	private String idInserted = "";
	private String emailInsert = "";
	private String nipInsert = "";
	
	@Test
	@Order(1)
	public void insert() throws Exception {
		ConnHandler.begin();
		
		Roles roles = new Roles();
		roles.setNameRole("HRD");
		roles.setCode("HR");
		roles.setCreatedBy("1");
		roles.setCreatedAt(LocalDateTime.now());
		roles.setIsActive(true);
		
		rolesDao.saveOrUpdate(roles);
		
		Company company = new Company();
		company.setNames("Lawencon");
		company.setCode("LWN");
		company.setDescription("Perusahaan A");
		company.setAddress("Jl.Tebet Raya");
		company.setEmail("lawencon@gmail.com");
		company.setWebsite("www.lawencon.com");
		company.setPhoneNumber("085432345667");
		company.setFax("02198765");
		company.setCreatedBy("2");
		company.setCreatedAt(LocalDateTime.now());
		company.setIsActive(true);
		
		companyDao.saveOrUpdate(company);
		
		Employee employee = new Employee();
		employee.setNameEmployee("Bhondan");
		employee.setNip("3171767886674");
		employee.setPhoneNumber("086545664554");
		employee.setGender("Laki-Laki");
		employee.setCreatedBy("1");
		employee.setCreatedAt(LocalDateTime.now());
		employee.setIsActive(true);
		
		employeeDao.saveOrUpdate(employee);
		
		Users users = new Users();
		users.setEmail("bhondan@gmail.com");
		users.setPass("bondanlalayeye");
		users.setRolesId(roles);
		users.setEmployeeId(employee);
		users.setCreatedBy("1");
		users.setCreatedAt(LocalDateTime.now());
		users.setIsActive(true);
		
		usersDao.saveOrUpdate(users);
		
		ConnHandler.commit();
		
		idInserted = users.getId();
		nipInsert = employee.getNip();

		assertNotNull(users.getId());
	}
	
	@Test
	@Order(2)
	public void update() throws Exception {
		Users users = usersDao.findById(idInserted);

		ConnHandler.begin();
		
		users.setEmail("bhondan@lawencon.com");
		users.setUpdatedBy("1");
		users.setUpdatedAt(LocalDateTime.now());
		users.setIsActive(true);
		users.setVersion(0L);
		
		usersDao.saveOrUpdate(users);
		
		ConnHandler.commit();

		Users usersCheck = usersDao.findById(idInserted);
		emailInsert = usersCheck.getEmail();
		assertEquals(1, usersCheck.getVersion());
		assertEquals("bhondan@lawencon.com", usersCheck.getEmail());
	}
	
	@Test
	@Order(3)
	public void checkAll() throws Exception{
		List<Users> usersList = usersDao.findAll();
		assertNotNull(usersList);
	}
	
	@Test
	@Order(4)
	public void checkById() throws Exception {
		Users users = usersDao.findById(idInserted);
		assertNotNull(users);
	}
	
	@Test
	@Order(5)
	public void checkByEmail() throws Exception {
		Users users = usersDao.getEmail(emailInsert);
		assertNotNull(users);
	}
	
	@Test
	@Order(6)
	public void checkByNip() throws Exception {
		Users users = usersDao.getByNip(nipInsert);
		assertNotNull(users);
	}
	
	@Test
	@Order(7)
	public void delete() throws Exception {
		ConnHandler.begin();
		boolean isDeleted = usersDao.deleteById(idInserted);
		ConnHandler.commit();

		assertTrue(isDeleted);
	}

}
