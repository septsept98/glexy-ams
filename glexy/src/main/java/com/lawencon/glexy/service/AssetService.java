package com.lawencon.glexy.service;

import java.util.List;

import com.lawencon.glexy.dto.asset.InsertReqDataAsset;
import com.lawencon.glexy.model.Asset;

public interface AssetService {
	
	Asset saveOrUpdate(InsertReqDataAsset data) throws Exception;
	
	Asset findById(String id) throws Exception;
	
	List<Asset> findAll() throws Exception;
	
	boolean removeById(String id) throws Exception;
}
