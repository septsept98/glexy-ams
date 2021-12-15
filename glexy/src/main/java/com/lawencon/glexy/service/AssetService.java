package com.lawencon.glexy.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.lawencon.glexy.dto.asset.InsertReqDataAsset;
import com.lawencon.glexy.model.Asset;

public interface AssetService {
	
	Asset save(InsertReqDataAsset data, MultipartFile invoiceImg, MultipartFile assetImg) throws Exception;
	
	Asset update(Asset data) throws Exception;
	
	Asset findById(String id) throws Exception;
	
	List<Asset> findAll() throws Exception;
	
	boolean removeById(String id) throws Exception;
	
	String generateCode(String invenCode, int stock, int index) throws Exception;
}
