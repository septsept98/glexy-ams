package com.lawencon.glexy.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.lawencon.glexy.model.Company;

public interface CompanyService {
	
	Company save(Company data, MultipartFile files) throws Exception;
	
	Company update(Company data) throws Exception;

	Company findById(String id) throws Exception;

	List<Company> findAll() throws Exception;
	
	boolean removeById(String id) throws Exception;

}
