package com.lawencon.glexy.dao;

import java.util.List;

import com.lawencon.glexy.model.Invoice;

public interface InvoiceDao {

	Invoice saveOrUpdate(Invoice data) throws Exception;

	Invoice findById(String id) throws Exception;
	
	List<Invoice> findAll() throws Exception;
	
	boolean deleteById(String id) throws Exception;
}