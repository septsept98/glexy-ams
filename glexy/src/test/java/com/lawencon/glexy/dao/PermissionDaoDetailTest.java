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
import com.lawencon.glexy.model.PermissionDetail;
import com.lawencon.glexy.model.Permissions;
import com.lawencon.glexy.model.Roles;


@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
@DataJpaTest 
class PermissionDaoDetailTest {
	
	@Autowired
	private PermissionsDao permissionsDao;
	
	@Autowired
	private RolesDao rolesDao;
	
	@Autowired
	private PermissionDetailDao permissionDetailDao;
	
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
		
		Permissions permissions = new Permissions();
		permissions.setNamePermission("master");
		permissions.setCode("MA");
		permissions.setCreatedBy("1");
		permissions.setCreatedAt(LocalDateTime.now());
		permissions.setIsActive(true);
		
		permissionsDao.saveOrUpdate(permissions);
		
		PermissionDetail permissionDetail = new PermissionDetail();
		permissionDetail.setRolesId(roles);
		permissionDetail.setPermissionsId(permissions);
		permissionDetail.setCreatedBy("1");
		permissionDetail.setCreatedAt(LocalDateTime.now());
		permissionDetail.setIsActive(true);
		
		permissionDetailDao.saveOrUpdate(permissionDetail);
		
		ConnHandler.commit();
		
		idInserted = permissionDetail.getId();

		assertNotNull(permissionDetail.getId());
	}
	
	@Test
	@Order(2)
	public void update() throws Exception {
		PermissionDetail permissionDetail = permissionDetailDao.findById(idInserted);

		ConnHandler.begin();
		
		Permissions permissions = new Permissions();
		permissions.setNamePermission("transaction");
		permissions.setCode("TR");
		permissions.setCreatedBy("1");
		permissions.setCreatedAt(LocalDateTime.now());
		permissions.setIsActive(true);
		
		permissionsDao.saveOrUpdate(permissions);
		
		permissionDetail.setPermissionsId(permissions);
		permissionDetail.setUpdatedBy("1");
		permissionDetail.setUpdatedAt(LocalDateTime.now());
		permissionDetail.setIsActive(true);
		permissionDetail.setVersion(0L);
		
		permissionDetailDao.saveOrUpdate(permissionDetail);
		
		ConnHandler.commit();

		PermissionDetail permisDetCheck = permissionDetailDao.findById(idInserted);

		assertEquals(1, permisDetCheck.getVersion());
		assertEquals(permissions, permisDetCheck.getPermissionsId());
	}
	
	@Test
	@Order(3)
	public void checkAll() throws Exception{
		List<PermissionDetail>permisDetList = permissionDetailDao.findAll();
		assertNotNull(permisDetList);
	}
	
	@Test
	@Order(4)
	public void checkById() throws Exception {
		PermissionDetail permissionDetail = permissionDetailDao.findById(idInserted);
		assertNotNull(permissionDetail);
	}
	
	@Test
	@Order(5)
	public void delete() throws Exception {
		ConnHandler.begin();
		boolean isDeleted = permissionDetailDao.deleteById(idInserted);
		ConnHandler.commit();

		assertTrue(isDeleted);
	}

}
