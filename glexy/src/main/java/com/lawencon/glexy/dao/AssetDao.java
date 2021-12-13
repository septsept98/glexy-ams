package com.lawencon.glexy.dao;

import java.util.List;

import com.lawencon.glexy.model.Asset;

public interface AssetDao {
	
	void insert(Asset data) throws Exception;
	
	void update(Asset data) throws Exception;

	Asset findById(String id) throws Exception;
	
	List<Asset> findAll() throws Exception;
	
	

}
