package com.lawencon.glexy.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.lawencon.base.ConnHandler;
import com.lawencon.glexy.model.Employee;

@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
@DataJpaTest
class EmployeeDaoTest {
	
	@Autowired
	private EmployeeDao employeeDao;
	
	private String idInserted = "";
	
	@Test
	@Order(1)
	public void insert() throws Exception {
		ConnHandler.begin();
		
		Employee employee = new Employee();
		employee.setNameEmployee("Bhondan");
		employee.setNip("3171767886674");
		employee.setPhoneNumber("086545664554");
		employee.setGender("Laki-Laki");
		employee.setCreatedBy("1");
		employee.setCreatedAt(LocalDateTime.now());
		employee.setIsActive(true);
		
		employeeDao.saveOrUpdate(employee);
		
		ConnHandler.commit();
		
		idInserted = employee.getId();

		assertNotNull(employee.getId());
	}
	
	@Test
	@Order(2)
	public void update() throws Exception {
		Employee employee = employeeDao.findById(idInserted);

		ConnHandler.begin();
		
		employee.setNameEmployee("Bondan");
		employee.setUpdatedBy("1");
		employee.setUpdatedAt(LocalDateTime.now());
		employee.setIsActive(true);
		employee.setVersion(0L);
		
		employeeDao.saveOrUpdate(employee);
		
		ConnHandler.commit();

		Employee employeeCheck = employeeDao.findById(idInserted);

		assertEquals(1, employeeCheck.getVersion());
		assertEquals("Bondan", employeeCheck.getNameEmployee());
	}
	
	@Test
	@Order(3)
	public void checkAll() throws Exception{
		List<Employee> employeeList = employeeDao.findAll();
		assertNotNull(employeeList);
	}
	
	@Test
	@Order(4)
	public void checkById() throws Exception {
		Employee employee = employeeDao.findById(idInserted);
		assertNotNull(employee);
	}
	
	@Test
	@Order(5)
	public void delete() throws Exception {
		ConnHandler.begin();
		boolean isDeleted = employeeDao.deleteById(idInserted);
		ConnHandler.commit();

		assertTrue(isDeleted);
	}

}
