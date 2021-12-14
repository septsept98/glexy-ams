package com.lawencon.glexy.service;

import java.util.List;

import com.lawencon.glexy.model.Brand;

public interface BrandService {
	
	Brand saveOrUpdate(Brand data) throws Exception;

	Brand findById(String id) throws Exception;

	List<Brand> findAll() throws Exception;
	
	boolean removeById(String id) throws Exception;
	
}
