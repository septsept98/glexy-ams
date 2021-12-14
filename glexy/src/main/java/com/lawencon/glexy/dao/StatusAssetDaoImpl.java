package com.lawencon.glexy.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.BaseDaoImpl;
import com.lawencon.glexy.model.StatusAsset;

@Repository
public class StatusAssetDaoImpl extends BaseDaoImpl<StatusAsset> implements StatusAssetDao {

	@Override
	public List<StatusAsset> findAll() throws Exception {
		return getAll();
	}

	@Override
	public StatusAsset findById(String id) throws Exception {
		return getById(id);
	}

	@Override
	public StatusAsset saveOrUpdate(StatusAsset data) throws Exception {
		data = save(data);
		return data;
	}

	@Override
	public boolean removeById(String id) throws Exception {
		return deleteById(id);
	}

}
