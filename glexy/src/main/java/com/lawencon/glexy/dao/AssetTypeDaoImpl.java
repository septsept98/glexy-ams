package com.lawencon.glexy.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.BaseDaoImpl;
import com.lawencon.glexy.model.AssetType;

@Repository
public class AssetTypeDaoImpl extends BaseDaoImpl<AssetType> implements AssetTypeDao{

	@Override
	public AssetType saveOrUpdate(AssetType data) throws Exception {
		return save(data);
	}

	@Override
	public boolean removeById(String id) throws Exception {
		return deleteById(id);
	}

	@Override
	public AssetType findById(String id) throws Exception {
		return getById(id);
	}

	@Override
	public List<AssetType> findAll() throws Exception {
		return getAll();
	}
	

}
