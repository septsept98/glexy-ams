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
import com.lawencon.glexy.model.Inventory;

@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
@DataJpaTest 
class InventoryDaoTest {
	
	@Autowired
	private InventoryDao inventoryDao;
	
	private String idInserted = "";
	private String codeInsert = "";
	
	@Test
	@Order(1)
	public void insert() throws Exception {
		ConnHandler.begin();
		
		Inventory inventory = new Inventory();
		inventory.setNameAsset("Laptop");
		inventory.setCode("LTP");
		inventory.setStock(1);
		inventory.setLatestStock(1);
		inventory.setCreatedBy("2");
		inventory.setCreatedAt(LocalDateTime.now());
		inventory.setIsActive(true);
		
		inventoryDao.saveOrUpdate(inventory);
		
		ConnHandler.commit();
		
		idInserted = inventory.getId();
		codeInsert = inventory.getCode();

		assertNotNull(inventory.getId());
	}
	
	@Test
	@Order(2)
	public void update() throws Exception {
		Inventory inventory = inventoryDao.findById(idInserted);

		ConnHandler.begin();
		
		inventory.setNameAsset("Komputer");
		inventory.setUpdatedBy("3");
		inventory.setUpdatedAt(LocalDateTime.now());
		inventory.setIsActive(true);
		inventory.setVersion(0L);
		
		inventoryDao.saveOrUpdate(inventory);
		ConnHandler.commit();

		Inventory inventoryCheck = inventoryDao.findById(idInserted);

		assertEquals(1, inventoryCheck.getVersion());
		assertEquals("Komputer", inventoryCheck.getNameAsset());
	}
	
	@Test
	@Order(3)
	public void checkAll() throws Exception{
		List<Inventory> inventoryList = inventoryDao.findAll();
		assertNotNull(inventoryList);
	}
	
	@Test
	@Order(4)
	public void checkById() throws Exception {
		Inventory inventory = inventoryDao.findById(idInserted);
		assertNotNull(inventory);
	}
	
	@Test
	@Order(5)
	public void checkByCode() throws Exception {
		Inventory inventory = inventoryDao.findByCode(codeInsert);
		assertNotNull(inventory);
	}
	
	@Test
	@Order(6)
	public void delete() throws Exception {
		ConnHandler.begin();
		boolean isDeleted = inventoryDao.removeById(idInserted);
		ConnHandler.commit();

		assertTrue(isDeleted);
	}

}
