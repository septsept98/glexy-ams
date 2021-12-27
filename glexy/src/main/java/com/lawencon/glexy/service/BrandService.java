package com.lawencon.glexy.service;

import java.util.List;

import com.lawencon.glexy.model.Brand;

public interface BrandService {
	
	Brand saveOrUpdate(Brand data) throws Exception;

	Brand findById(String id) throws Exception;
	
	List<Brand> searchByNameCode(String search) throws Exception;
	
	Brand findByCode(String code) throws Exception;

	List<Brand> findAll() throws Exception;
	
	boolean removeById(String id) throws Exception;
	
	void validationFk(String id) throws Exception;
	
	void validationSave(Brand data) throws Exception;

	void validationUpdate(Brand data) throws Exception;
	
}
