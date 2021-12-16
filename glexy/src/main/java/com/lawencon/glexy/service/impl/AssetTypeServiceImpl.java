package com.lawencon.glexy.service.impl;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.glexy.dao.AssetTypeDao;
import com.lawencon.glexy.model.AssetType;
import com.lawencon.glexy.service.AssetTypeService;

@Service
public class AssetTypeServiceImpl extends BaseServiceImpl implements AssetTypeService {

	@Autowired
	private AssetTypeDao assetTypeDao;
	
	@Override
	public AssetType saveOrUpdate(AssetType data) throws Exception {
		try {
			if(data.getId() != null) {
				AssetType assetType = findById(data.getId());
				data.setCreatedAt(assetType.getCreatedAt());
				data.setCreatedBy(assetType.getCreatedBy());
				data.setUpdatedBy("1");
				data.setVersion(assetType.getVersion());
			} else {
				data.setCreatedBy("3");
			}
			
			begin();
			data = assetTypeDao.saveOrUpdate(data);
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
		}
		return data;
	}

	@Override
	public AssetType findById(String id) throws Exception {
		AssetType result = new AssetType();
		try {
			result = assetTypeDao.findById(id);
		} catch (NoResultException e) {
			e.printStackTrace();
			throw new NoResultException("Asset Type not found");
		}
		return result;
	}

	@Override
	public List<AssetType> findAll() throws Exception {
		return assetTypeDao.findAll();
	}

	@Override
	public boolean removeById(String id) throws Exception {
		boolean result = false;
		try {
			begin();
			result = assetTypeDao.removeById(id);
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
		}
		return result;
	}
	
	
	

}
