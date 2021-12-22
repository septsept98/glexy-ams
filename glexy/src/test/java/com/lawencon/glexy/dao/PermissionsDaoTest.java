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
import com.lawencon.glexy.model.Permissions;

@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
@DataJpaTest 
class PermissionsDaoTest {
	
	@Autowired
	private PermissionsDao permissionsDao;
	
	private String idInserted = "";
	
	@Test
	@Order(1)
	public void insert() throws Exception {
		ConnHandler.begin();
		
		Permissions permissions = new Permissions();
		permissions.setNamePermission("master");
		permissions.setCode("MA");
		permissions.setCreatedBy("1");
		permissions.setCreatedAt(LocalDateTime.now());
		permissions.setIsActive(true);
		
		permissionsDao.saveOrUpdate(permissions);
		
		ConnHandler.commit();
		
		idInserted = permissions.getId();

		assertNotNull(permissions.getId());
	}
	
	@Test
	@Order(2)
	public void update() throws Exception {
		Permissions permissions = permissionsDao.findById(idInserted);

		ConnHandler.begin();
		
		permissions.setNamePermission("insert");
		permissions.setUpdatedBy("1");
		permissions.setUpdatedAt(LocalDateTime.now());
		permissions.setIsActive(true);
		permissions.setVersion(0L);
		
		permissionsDao.saveOrUpdate(permissions);
		
		ConnHandler.commit();

		Permissions permissionsCheck = permissionsDao.findById(idInserted);

		assertEquals(1, permissionsCheck.getVersion());
		assertEquals("insert", permissionsCheck.getNamePermission());
	}
	
	@Test
	@Order(3)
	public void checkAll() throws Exception{
		List<Permissions> permissionList = permissionsDao.findAll();
		assertNotNull(permissionList);
	}
	
	@Test
	@Order(4)
	public void checkById() throws Exception {
		Permissions permissions = permissionsDao.findById(idInserted);
		assertNotNull(permissions);
	}
	
	@Test
	@Order(5)
	public void delete() throws Exception {
		ConnHandler.begin();
		boolean isDeleted = permissionsDao.deleteById(idInserted);
		ConnHandler.commit();

		assertTrue(isDeleted);
	}

}
