package com.lawencon.glexy.service;

import java.util.List;

import com.lawencon.glexy.model.Asset;

public interface AssetService {
	
	Asset saveOrUpdate(Asset data) throws Exception;
	
	Asset findById(String id) throws Exception;
	
	List<Asset> findAll() throws Exception;
	
	boolean deleteById(String id) throws Exception;
}
