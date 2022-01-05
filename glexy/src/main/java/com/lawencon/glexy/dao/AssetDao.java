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
	
	List<Asset> findExpiredAsset() throws Exception;

	List<Asset> findByAssetTypeId(String id) throws Exception;
	
	List<Asset> findByInventoryId(String id) throws Exception;
	
	List<Asset> findByInvoiceId(String id) throws Exception;
	
	List<Asset> findByStatusAssetId(String id) throws Exception;
	
	List<Asset> searchAssetGeneral(String search) throws Exception;
	
	List<Asset> findAssetByInventBrand(String inventId, String brandId) throws Exception;
	
	List<Asset> findAssetUndeployable() throws Exception;
}
