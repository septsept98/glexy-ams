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
import com.lawencon.glexy.model.Roles;

@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
@DataJpaTest 
class RolesDaoTest {
	
	@Autowired
	private RolesDao rolesDao;
	
	private String idInserted = "";
	
	@Test
	@Order(1)
	public void insert() throws Exception {
		ConnHandler.begin();
		
		Roles roles = new Roles();
		roles.setNameRole("Admin");
		roles.setCode("ADM");
		roles.setCreatedBy("1");
		roles.setCreatedAt(LocalDateTime.now());
		roles.setIsActive(true);
		
		rolesDao.saveOrUpdate(roles);
		
		ConnHandler.commit();
		
		idInserted = roles.getId();

		assertNotNull(roles.getId());
	}
	
	@Test
	@Order(2)
	public void update() throws Exception {
		Roles roles = rolesDao.findById(idInserted);

		ConnHandler.begin();
		
		roles.setNameRole("Adminn");
		roles.setUpdatedBy("1");
		roles.setUpdatedAt(LocalDateTime.now());
		roles.setIsActive(true);
		roles.setVersion(0L);
		
		rolesDao.saveOrUpdate(roles);
		
		ConnHandler.commit();

		Roles rolesCheck = rolesDao.findById(idInserted);

		assertEquals(1, rolesCheck.getVersion());
		assertEquals("Adminn", rolesCheck.getNameRole());
	}
	
	@Test
	@Order(3)
	public void checkAll() throws Exception{
		List<Roles> rolesList = rolesDao.findAll();
		assertNotNull(rolesList);
	}
	
	@Test
	@Order(4)
	public void checkById() throws Exception {
		Roles roles = rolesDao.findById(idInserted);
		assertNotNull(roles);
	}
	
	@Test
	@Order(5)
	public void delete() throws Exception {
		ConnHandler.begin();
		boolean isDeleted = rolesDao.deleteById(idInserted);
		ConnHandler.commit();

		assertTrue(isDeleted);
	}

}
