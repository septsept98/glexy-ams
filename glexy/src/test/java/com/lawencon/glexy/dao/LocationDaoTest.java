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
import com.lawencon.glexy.model.Company;
import com.lawencon.glexy.model.Location;

@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
@DataJpaTest 
class LocationDaoTest {
	
	@Autowired
	private LocationDao locationDao;
	@Autowired
	private CompanyDao companyDao;
	
	private String idInserted = "";

	@Test
	@Order(1)
	public void insert() throws Exception {
		ConnHandler.begin();
		
		Company company = new Company();
		company.setNames("Lawencon");
		company.setCode("LWN");
		company.setDescription("Perusahaan A");
		company.setAddress("Jl.Tebet Raya");
		company.setEmail("lawencon@gmail.com");
		company.setWebsite("www.lawencon.com");
		company.setPhoneNumber("085432345667");
		company.setFax("02198765");
		company.setCreatedBy("2");
		company.setCreatedAt(LocalDateTime.now());
		company.setIsActive(true);
		
		companyDao.saveOrUpdate(company);
		
		Location location = new Location();
		location.setNamePlace("Ruang Bootcamp");
		location.setCode("BTP");
		location.setCompanyId(company);
		location.setCreatedBy("2");
		location.setCreatedAt(LocalDateTime.now());
		location.setIsActive(true);
		
		locationDao.saveOrUpdate(location);
		
		ConnHandler.commit();
		
		idInserted = location.getId();

		assertNotNull(location.getId());
	}
	
	@Test
	@Order(2)
	public void update() throws Exception {
		Location location = locationDao.findById(idInserted);

		ConnHandler.begin();
		
		location.setNamePlace("Ruang Meeting");
		location.setUpdatedBy("3");
		location.setUpdatedAt(LocalDateTime.now());
		location.setIsActive(true);
		location.setVersion(0L);
		
		locationDao.saveOrUpdate(location);
		ConnHandler.commit();

		Location locationCheck = locationDao.findById(idInserted);

		assertEquals(1, locationCheck.getVersion());
		assertEquals("Ruang Meeting", locationCheck.getNamePlace());
	}
	
	@Test
	@Order(3)
	public void checkAll() throws Exception{
		List<Location> locationList = locationDao.findAll();
		assertNotNull(locationList);
	}
	
	@Test
	@Order(4)
	public void checkById() throws Exception {
		Location location = locationDao.findById(idInserted);
		assertNotNull(location);
	}
	
	@Test
	@Order(5)
	public void delete() throws Exception {
		ConnHandler.begin();
		boolean isDeleted = locationDao.removeById(idInserted);
		ConnHandler.commit();

		assertTrue(isDeleted);
	}

}
