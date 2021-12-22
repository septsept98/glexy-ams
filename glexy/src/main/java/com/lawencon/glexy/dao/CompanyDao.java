package com.lawencon.glexy.dao;

import java.util.List;

import com.lawencon.glexy.model.Company;

public interface CompanyDao {
	
	Company saveOrUpdate(Company data) throws Exception;

	Company findById(String id) throws Exception;
	
	Company findByCode(String code) throws Exception;
	
	List<Company> findAll() throws Exception;
	
	boolean removeById(String id) throws Exception;
	
	

}
