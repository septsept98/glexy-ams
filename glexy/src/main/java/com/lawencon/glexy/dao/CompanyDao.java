package com.lawencon.glexy.dao;

import java.util.List;

import com.lawencon.glexy.model.Company;

public interface CompanyDao {
	
	void insert(Company data) throws Exception;

	void update(Company data) throws Exception;

	Company findById(String id) throws Exception;
	
	List<Company> findAll() throws Exception;
	

}
