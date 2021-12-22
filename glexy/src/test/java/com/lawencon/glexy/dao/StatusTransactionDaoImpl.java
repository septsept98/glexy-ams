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
import com.lawencon.glexy.model.StatusTransaction;

@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
@DataJpaTest
class StatusTransactionDaoImpl {
	
	@Autowired
	private StatusTransactionDao statusTransactionDao;
	
	@Autowired
	private StatusAssetDao statusAssetDao;
	
	private String idInserted = "";
	
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
		
		StatusTransaction statusTransaction = new StatusTransaction();
		statusTransaction.setCodeStatusTr("RTD");
		statusTransaction.setNameStatusTr("Ready to Deploy");
		statusTransaction.setStatusAssetId(statusAsset);
		statusTransaction.setCreatedBy("2");
		statusTransaction.setCreatedAt(LocalDateTime.now());
		statusTransaction.setIsActive(true);
		
		statusTransactionDao.saveOrUpdate(statusTransaction);
		
		ConnHandler.commit();
		
		idInserted = statusTransaction.getId();

		assertNotNull(statusTransaction.getId());
	}
	
	@Test
	@Order(2)
	public void update() throws Exception {
		StatusTransaction statusTransaction = statusTransactionDao.findById(idInserted);

		ConnHandler.begin();
		
		statusTransaction.setNameStatusTr("Deploy");
		statusTransaction.setUpdatedBy("1");
		statusTransaction.setUpdatedAt(LocalDateTime.now());
		statusTransaction.setIsActive(true);
		statusTransaction.setVersion(0L);
		
		statusTransactionDao.saveOrUpdate(statusTransaction);
		
		ConnHandler.commit();

		StatusTransaction sTransCheck = statusTransactionDao.findById(idInserted);

		assertEquals(1, sTransCheck.getVersion());
		assertEquals("Deploy", sTransCheck.getNameStatusTr());
	}
	
	@Test
	@Order(3)
	public void checkAll() throws Exception{
		List<StatusTransaction> sTransList = statusTransactionDao.findAll();
		assertNotNull(sTransList);
	}
	
	@Test
	@Order(4)
	public void checkById() throws Exception {
		StatusTransaction statusTransaction = statusTransactionDao.findById(idInserted);
		assertNotNull(statusTransaction);
	}
	
	@Test
	@Order(5)
	public void delete() throws Exception {
		ConnHandler.begin();
		boolean isDeleted = statusTransactionDao.removeById(idInserted);
		ConnHandler.commit();

		assertTrue(isDeleted);
	}

}
