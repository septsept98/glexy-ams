package com.lawencon.glexy.dao;

import java.util.List;

import com.lawencon.glexy.model.StatusAsset;

public interface StatusAssetDao {

	List<StatusAsset> findAll() throws Exception;

	StatusAsset findById(String id) throws Exception;

	StatusAsset saveOrUpdate(StatusAsset data) throws Exception;

	boolean deleteById(String id) throws Exception;
	
}
