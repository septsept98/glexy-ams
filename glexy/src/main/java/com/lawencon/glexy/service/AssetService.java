package com.lawencon.glexy.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;


import com.lawencon.glexy.dto.InsertResDto;

import com.lawencon.glexy.model.Asset;

public interface AssetService {
	
	Asset save(Asset data, MultipartFile invoiceImg, MultipartFile assetImg) throws Exception;
	
	Asset update(Asset data) throws Exception;
	
	Asset findById(String id) throws Exception;
	
	List<Asset> findByInvent(String idInvent) throws Exception;
	
	List<Asset> findByBrandId(String idBrand) throws Exception;
	
	List<Asset> findByCompanyId(String idCompany) throws Exception;
	
	List<Asset> findAll() throws Exception;
	
	boolean removeById(String id) throws Exception;
	
	List<Asset> findAllDeployAsset() throws Exception;
	
	List<Asset> findAllGeneralAsset() throws Exception;

	String generateCode(String invenCode, String codeCompany, int stock, int index) throws Exception;
	
	boolean hasExcelFormat(MultipartFile file) throws Exception;
	
	Asset saveExcel(MultipartFile file) throws Exception;
	
	Asset updateImage(String id, MultipartFile assetImg) throws Exception;

}
