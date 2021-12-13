package com.lawencon.glexy.dao;

import java.util.List;

import com.lawencon.base.BaseDaoImpl;
import com.lawencon.glexy.model.Asset;

public class AssetDaoImpl extends BaseDaoImpl<Asset> implements AssetDao{

	@Override
	public void insert(Asset data) throws Exception {
		save(data);
	}

	@Override
	public void update(Asset data) throws Exception {
		save(data);
	}

	@Override
	public Asset findById(String id) throws Exception {
		return getById(id);
	}

	@Override
	public List<Asset> findAll() throws Exception {
		return getAll();
	}


	
	
	
}
