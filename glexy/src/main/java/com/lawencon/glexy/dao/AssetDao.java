package com.lawencon.glexy.dao;

import java.util.List;

import com.lawencon.glexy.model.Asset;

public interface AssetDao {
	
	Asset saveOrUpdate(Asset data) throws Exception;

	Asset findById(String id) throws Exception;
	
	List<Asset> findByInvent(String idInvent) throws Exception;
	
	List<Asset> findAll() throws Exception;
	
	boolean removeById(String id) throws Exception;
	
	List<Asset> findByBrandId(String brandId) throws Exception;
	
	List<Asset> findByCompanyId(String companyId) throws Exception;
	
	List<Asset> findAllDeployAsset() throws Exception;
	
	List<Asset> findAllGeneralAsset() throws Exception;
	
	List<Asset> findByAssetTypeId(String Id) throws Exception;
}
