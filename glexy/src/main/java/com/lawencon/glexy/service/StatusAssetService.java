package com.lawencon.glexy.service;

import java.util.List;

import com.lawencon.glexy.model.StatusAsset;

public interface StatusAssetService {

	StatusAsset saveOrUpdate(StatusAsset data) throws Exception;
	
	boolean removeById(String id) throws Exception;
	
	StatusAsset findById(String id) throws Exception;
	
	List<StatusAsset> findAll() throws Exception;
}
