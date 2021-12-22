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
import com.lawencon.glexy.model.Brand;

@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
@DataJpaTest 
class BrandDaoTest {
	
	@Autowired
	private BrandDao brandDao;
	
	private String idInserted = "";
	private String codeInsert = "";
	
	@Test
	@Order(1)
	public void insert() throws Exception {
		ConnHandler.begin();
		
		Brand brand = new Brand();
		brand.setNames("Acer");
		brand.setCode("ACR");
		brand.setCreatedBy("2");
		brand.setCreatedAt(LocalDateTime.now());
		brand.setIsActive(true);
		
		brandDao.saveOrUpdate(brand);
		
		ConnHandler.commit();
		
		idInserted = brand.getId();
		codeInsert = brand.getCode();

		assertNotNull(brand.getId());
	}
	
	@Test
	@Order(2)
	public void update() throws Exception {
		Brand brand = brandDao.findById(idInserted);

		ConnHandler.begin();
		
		brand.setNames("ACHER");
		brand.setUpdatedBy("3");
		brand.setUpdatedAt(LocalDateTime.now());
		brand.setIsActive(true);
		brand.setVersion(0L);
		
		brandDao.saveOrUpdate(brand);
		
		ConnHandler.commit();

		Brand brandCheck = brandDao.findById(idInserted);

		assertEquals(1, brandCheck.getVersion());
		assertEquals("ACHER", brandCheck.getNames());
	}
	
	@Test
	@Order(3)
	public void checkAll() throws Exception{
		List<Brand> brandList = brandDao.findAll();
		assertNotNull(brandList);
	}
	
	@Test
	@Order(4)
	public void checkById() throws Exception {
		Brand brand = brandDao.findById(idInserted);
		assertNotNull(brand);
	}
	
	@Test
	@Order(5)
	public void checkByCode() throws Exception {
		Brand brand = brandDao.findByCode(codeInsert);
		assertNotNull(brand);
	}
	
	@Test
	@Order(6)
	public void delete() throws Exception {
		ConnHandler.begin();
		boolean isDeleted = brandDao.removeById(idInserted);
		ConnHandler.commit();

		assertTrue(isDeleted);
	}

}
