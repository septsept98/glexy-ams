package com.lawencon.glexy.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.lawencon.base.ConnHandler;
import com.lawencon.glexy.model.Company;

@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
@DataJpaTest
class CompanyDaoTest {
	
	@Autowired
	private CompanyDao companyDao;
	
	private String idInserted = "";
	private String codeInsert = "";
	
	
	@Test
	@Order(1)
	public void insert() throws Exception {
		ConnHandler.begin();
		
		Company company = new Company();
		company.setNames("Linov");
		company.setCode("LNV");
		company.setDescription("Perusahaan B");
		company.setAddress("Jl.Tebet Raya");
		company.setEmail("linovn@gmail.com");
		company.setWebsite("www.linov.com");
		company.setPhoneNumber("085432345667");
		company.setFax("02198765");
		company.setCreatedBy("2");
		company.setCreatedAt(LocalDateTime.now());
		company.setIsActive(true);
		
		companyDao.saveOrUpdate(company);
		
		ConnHandler.commit();
		
		codeInsert = company.getCode();
		idInserted = company.getId();

		assertNotNull(company.getId());
	}
	
	@Test
	@Order(2)
	public void update() throws Exception {
		Company company = companyDao.findById(idInserted);

		ConnHandler.begin();
		
		company.setAddress("Jl. Tebet Raya 2");
		company.setUpdatedBy("3");
		company.setUpdatedAt(LocalDateTime.now());
		company.setIsActive(true);
		company.setVersion(0L);
		
		ConnHandler.commit();

		Company companyCheck = companyDao.findById(idInserted);

		assertEquals(1, companyCheck.getVersion());
		assertEquals("Jl. Tebet Raya 2", companyCheck.getAddress());
	}
	
	@Test
	@Order(3)
	public void checkAll() throws Exception{
		List<Company> companyList = companyDao.findAll();
		assertNotNull(companyList);
	}
	
	@Test
	@Order(4)
	public void checkById() throws Exception {
		Company company = companyDao.findById(idInserted);
		assertNotNull(company);
	}
	
	@Test
	@Order(5)
	public void checkByCode() throws Exception {
		Company company = companyDao.findByCode(codeInsert);
		assertNotNull(company);
	}

	@Test
	@Order(6)
	public void delete() throws Exception {
		ConnHandler.begin();
		boolean isDeleted = companyDao.removeById(idInserted);
		ConnHandler.commit();

		assertTrue(isDeleted);
	}

}
