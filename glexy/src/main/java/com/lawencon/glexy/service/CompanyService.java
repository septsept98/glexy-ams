package com.lawencon.glexy.service;

import java.util.List;

import com.lawencon.glexy.model.Company;

public interface CompanyService {
	
	Company saveOrUpdate(Company data) throws Exception;

	Company findById(String id) throws Exception;

	List<Company> findAll() throws Exception;
	
	boolean deleteById(String id) throws Exception;

}