package com.lawencon.glexy.dao;

import java.util.List;

import com.lawencon.glexy.model.Asset;

public interface AssetDao {
	
	Asset saveOrUpdate(Asset data) throws Exception;

	Asset findById(String id) throws Exception;
	
	List<Asset> findByInvent(String idInvent) throws Exception;
	
	List<Asset> findAll() throws Exception;
	
	boolean removeById(String id) throws Exception;
	
	

}
