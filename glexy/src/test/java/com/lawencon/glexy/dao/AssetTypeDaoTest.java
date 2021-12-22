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
import com.lawencon.glexy.model.AssetType;

@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
@DataJpaTest 
class AssetTypeDaoTest {
	
	@Autowired
	private AssetTypeDao assetTypeDao;
	
	private String idInserted = "";
	private String codeInsert = "";
	
	@Test
	@Order(1)
	public void insert() throws Exception {
		ConnHandler.begin();
		
		AssetType assetType = new AssetType();
		assetType.setNames("General");
		assetType.setCode("GNR");
		assetType.setCreatedBy("2");
		assetType.setCreatedAt(LocalDateTime.now());
		assetType.setIsActive(true);
		
		assetTypeDao.saveOrUpdate(assetType);
		
		ConnHandler.commit();
		
		idInserted = assetType.getId();
		codeInsert = assetType.getCode();

		assertNotNull(assetType.getId());
	}
	
	@Test
	@Order(2)
	public void update() throws Exception {
		AssetType assetType = assetTypeDao.findById(idInserted);

		ConnHandler.begin();
		
		assetType.setNames("Genral");
		assetType.setUpdatedBy("3");
		assetType.setUpdatedAt(LocalDateTime.now());
		assetType.setIsActive(true);
		assetType.setVersion(0L);
		
		assetTypeDao.saveOrUpdate(assetType);
		ConnHandler.commit();

		AssetType assetTypeCheck = assetTypeDao.findById(idInserted);

		assertEquals(1, assetTypeCheck.getVersion());
		assertEquals("Genral", assetTypeCheck.getNames());
	}
	
	@Test
	@Order(3)
	public void checkAll() throws Exception{
		List<AssetType> assetTypeList = assetTypeDao.findAll();
		assertNotNull(assetTypeList);
	}
	
	@Test
	@Order(4)
	public void checkById() throws Exception {
		AssetType assetType = assetTypeDao.findById(idInserted);
		assertNotNull(assetType);
	}
	
	@Test
	@Order(5)
	public void checkByCode() throws Exception {
		AssetType assetType = assetTypeDao.findByCode(codeInsert);
		assertNotNull(assetType);
	}
	
	@Test
	@Order(6)
	public void delete() throws Exception {
		ConnHandler.begin();
		boolean isDeleted = assetTypeDao.removeById(idInserted);
		ConnHandler.commit();

		assertTrue(isDeleted);
	}

}
