package com.lawencon.glexy.dao;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
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
import com.lawencon.glexy.model.TrackAsset;

@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
@DataJpaTest 
class TrackAssetDaoTest {
	
	@Autowired
	private TrackAssetDao trackAssetDao;
	
	private String idInserted = "";
	private String assetCodeInsert = "";
	private String trCodeInsert = "";
	
	@Test
	@Order(1)
	public void insert() throws Exception {
		ConnHandler.begin();
		
		TrackAsset trackAsset = new TrackAsset();
		trackAsset.setCodeAsset("LTPLWN1");
		trackAsset.setNameActivity("New");
		trackAsset.setDateActivity(LocalDate.now());
		trackAsset.setUserId("2");
		trackAsset.setTransactionCode("CCC");
		trackAsset.setCreatedBy("2");
		trackAsset.setCreatedAt(LocalDateTime.now());
		trackAsset.setIsActive(true);
		
		trackAssetDao.saveOrUpdate(trackAsset);
		
		ConnHandler.commit();
		
		idInserted = trackAsset.getId();
		assetCodeInsert = trackAsset.getCodeAsset();
		trCodeInsert = trackAsset.getTransactionCode();
		
		assertNotNull(trackAsset.getId());
	}
	
	@Test
	@Order(2)
	public void checkAll() throws Exception{
		List<TrackAsset> trackAssetList = trackAssetDao.findAll();
		assertNotNull(trackAssetList);
	}
	
	@Test
	@Order(3)
	public void checkById() throws Exception {
		TrackAsset trackAsset = trackAssetDao.findById(idInserted);
		assertNotNull(trackAsset);
	}
	
	@Test
	@Order(4)
	public void checkByAssetTr() throws Exception {
		List<TrackAsset> trackAssetList = trackAssetDao.findByAssetTr(assetCodeInsert, trCodeInsert);
		assertNotNull(trackAssetList);
	}
	
	@Test
	@Order(5)
	public void checkByAsset() throws Exception {
		List<TrackAsset> trackAssetList = trackAssetDao.findByAsset(assetCodeInsert);
		assertNotNull(trackAssetList);
	}


}
