package com.lawencon.glexy.dao;

import java.util.List;

import com.lawencon.glexy.model.AssetType;

public interface AssetTypeDao {
	
	AssetType saveOrUpdate(AssetType data) throws Exception;

	AssetType findById(String id) throws Exception;
	
	AssetType findByCode(String code) throws Exception;
	
	List<AssetType> findAll() throws Exception;
	
	List<AssetType> searchByNameCode(String search) throws Exception;
	
	boolean removeById(String id) throws Exception;
	

}
