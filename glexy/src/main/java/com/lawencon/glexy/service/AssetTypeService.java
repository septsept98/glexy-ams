package com.lawencon.glexy.service;

import java.util.List;

import com.lawencon.glexy.model.AssetType;

public interface AssetTypeService {
	
	AssetType saveOrUpdate(AssetType data) throws Exception;

	AssetType findById(String id) throws Exception;

	List<AssetType> findAll() throws Exception;
	
	boolean deleteById(String id) throws Exception;
	
}