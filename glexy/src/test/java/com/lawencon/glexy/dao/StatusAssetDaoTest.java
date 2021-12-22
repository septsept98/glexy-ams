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
import com.lawencon.glexy.model.StatusAsset;

@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
@DataJpaTest 
class StatusAssetDaoTest {
	
	@Autowired
	private StatusAssetDao statusAssetDao;
	
	private String idInserted = "";
	private String codeInsert = "";
	
	@Test
	@Order(1)
	public void insert() throws Exception {
		ConnHandler.begin();
		
		StatusAsset statusAsset = new StatusAsset();
		statusAsset.setCodeStatusAsset("SA1");
		statusAsset.setNameStatusAsset("Deployable");
		statusAsset.setCreatedBy("2");
		statusAsset.setCreatedAt(LocalDateTime.now());
		statusAsset.setIsActive(true);
		
		statusAssetDao.saveOrUpdate(statusAsset);
		
		ConnHandler.commit();
		
		idInserted = statusAsset.getId();
		codeInsert = statusAsset.getCodeStatusAsset();

		assertNotNull(statusAsset.getId());
	}
	
	@Test
	@Order(2)
	public void update() throws Exception {
		StatusAsset statusAsset = statusAssetDao.findById(idInserted);

		ConnHandler.begin();
		
		statusAsset.setNameStatusAsset("Deploy");
		statusAsset.setUpdatedBy("3");
		statusAsset.setUpdatedAt(LocalDateTime.now());
		statusAsset.setIsActive(true);
		statusAsset.setVersion(0L);
		
		statusAssetDao.saveOrUpdate(statusAsset);
		
		ConnHandler.commit();

		StatusAsset statAssetCheck = statusAssetDao.findById(idInserted);

		assertEquals(1, statAssetCheck.getVersion());
		assertEquals("Deploy", statAssetCheck.getNameStatusAsset());
	}
	
	@Test
	@Order(3)
	public void checkAll() throws Exception{
		List<StatusAsset> statAssetList = statusAssetDao.findAll();
		assertNotNull(statAssetList);
	}
	
	@Test
	@Order(4)
	public void checkById() throws Exception {
		StatusAsset statusAsset = statusAssetDao.findById(idInserted);
		assertNotNull(statusAsset);
	}
	
	@Test
	@Order(5)
	public void checkByCode() throws Exception {
		StatusAsset statusAsset = statusAssetDao.findByCode(codeInsert);
		assertNotNull(statusAsset);
	}
	
	@Test
	@Order(6)
	public void delete() throws Exception {
		ConnHandler.begin();
		boolean isDeleted = statusAssetDao.removeById(idInserted);
		ConnHandler.commit();

		assertTrue(isDeleted);
	}

}
