package com.lawencon.glexy.service;

import java.util.List;

import com.lawencon.glexy.model.Inventory;
import com.lawencon.glexy.model.Invoice;

public interface InvoiceService {
	
	Invoice saveOrUpdate(Invoice data) throws Exception;
	
	Invoice findById(String id) throws Exception;
	
	List<Invoice> findAll() throws Exception;
	
	boolean removeById(String id) throws Exception;
	
	void validationFk(String id) throws Exception;
	
	void validationSave(Invoice data) throws Exception;

	void validationUpdate(Invoice data) throws Exception;
	
}
