package com.lawencon.glexy.dao;

import java.util.List;

import com.lawencon.glexy.model.Brand;

public interface BrandDao {
	
	Brand saveOrUpdate(Brand data) throws Exception;

	Brand findById(String id) throws Exception;
	
	Brand findByCode(String code) throws Exception;
	
	List<Brand> findAll() throws Exception;
	
	boolean removeById(String id) throws Exception;
	
}
