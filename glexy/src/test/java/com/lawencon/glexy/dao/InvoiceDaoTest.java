package com.lawencon.glexy.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
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
import com.lawencon.glexy.model.Invoice;

@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
@DataJpaTest 
class InvoiceDaoTest {
	
	@Autowired
	private InvoiceDao invoiceDao;
	
	private String idInserted = "";
	
	@Test
	@Order(1)
	public void insert() throws Exception {
		ConnHandler.begin();
		
		Invoice invoice = new Invoice();
		invoice.setCode("BHIY355433");
		invoice.setPurchaseDate(LocalDate.now());
		BigDecimal bigDecimal = new BigDecimal(2500000);
		invoice.setTotalPrice(bigDecimal);
		invoice.setCreatedBy("2");
		invoice.setCreatedAt(LocalDateTime.now());
		invoice.setIsActive(true);
		
		invoiceDao.saveOrUpdate(invoice);
		
		ConnHandler.commit();
		
		idInserted = invoice.getId();

		assertNotNull(invoice.getId());
	}
	
	@Test
	@Order(2)
	public void update() throws Exception {
		Invoice invoice = invoiceDao.findById(idInserted);

		ConnHandler.begin();
		
		BigDecimal bigDecimal = new BigDecimal(2000000);
		invoice.setTotalPrice(bigDecimal);
		invoice.setUpdatedBy("3");
		invoice.setUpdatedAt(LocalDateTime.now());
		invoice.setIsActive(true);
		invoice.setVersion(0L);
		
		invoiceDao.saveOrUpdate(invoice);
		ConnHandler.commit();

		Invoice invoiceCheck = invoiceDao.findById(idInserted);

		assertEquals(1, invoiceCheck.getVersion());
		assertEquals(bigDecimal, invoiceCheck.getTotalPrice());
	}
	
	@Test
	@Order(3)
	public void checkAll() throws Exception{
		List<Invoice> invoiceList = invoiceDao.findAll();
		assertNotNull(invoiceList);
	}
	
	@Test
	@Order(4)
	public void checkById() throws Exception {
		Invoice invoice = invoiceDao.findById(idInserted);
		assertNotNull(invoice);
	}
	
	@Test
	@Order(5)
	public void delete() throws Exception {
		ConnHandler.begin();
		boolean isDeleted = invoiceDao.removeById(idInserted);
		ConnHandler.commit();

		assertTrue(isDeleted);
	}

}
