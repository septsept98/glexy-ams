package com.lawencon.glexy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.glexy.dao.AssetDao;
import com.lawencon.glexy.model.Asset;

@Service
public class AssetServiceImpl extends BaseServiceImpl implements AssetService {
	
	@Autowired
	private AssetDao assetDao;
	
	@Override
	public Asset saveOrUpdate(Asset data) throws Exception {
		try {
			if(data.getId() != null) {
				Asset asset = findById(data.getId());
				data.setCreatedAt(asset.getCreatedAt());
				data.setCreatedBy(asset.getCreatedBy());
				data.setVersion(asset.getVersion());
			}
			
			begin();
			data = assetDao.saveOrUpdate(data);
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
		}
		return data;
	}

	@Override
	public Asset findById(String id) throws Exception {
		return assetDao.findById(id);
	}

	@Override
	public List<Asset> findAll() throws Exception {
		return assetDao.findAll();
	}

	@Override
	public boolean deleteById(String id) throws Exception {
		return assetDao.deleteById(id);
	}
	
	
	
	

}
