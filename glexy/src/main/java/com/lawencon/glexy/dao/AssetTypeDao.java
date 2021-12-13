package com.lawencon.glexy.dao;

import java.util.List;

import com.lawencon.glexy.model.AssetType;

public interface AssetTypeDao {
	
	void insert(AssetType data) throws Exception;

	void update(AssetType data) throws Exception;

	AssetType findById(String id) throws Exception;
	
	List<AssetType> findAll() throws Exception;
	

}
