package com.lawencon.glexy.service;

import java.util.List;

import com.lawencon.glexy.model.Invoice;

public interface InvoiceService {
	
	Invoice saveOrUpdate(Invoice data) throws Exception;
	
	Invoice findById(String id) throws Exception;
	
	List<Invoice> findAll() throws Exception;
	
	boolean deleteById(String id) throws Exception;
	
}
