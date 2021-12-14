package com.lawencon.glexy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.glexy.dao.AssetTypeDao;
import com.lawencon.glexy.model.AssetType;

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
				data.setVersion(assetType.getVersion());
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
		return assetTypeDao.findById(id);
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
