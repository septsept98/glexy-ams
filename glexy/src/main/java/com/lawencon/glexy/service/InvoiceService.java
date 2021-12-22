package com.lawencon.glexy.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.lawencon.glexy.model.Invoice;

public interface InvoiceService {
	
	Invoice save(Invoice data) throws Exception;
	
	Invoice update(Invoice data, MultipartFile file) throws Exception;
	
	Invoice findById(String id) throws Exception;
	
	List<Invoice> findAll() throws Exception;
	
	boolean removeById(String id) throws Exception;
	
}
