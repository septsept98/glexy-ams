package com.lawencon.glexy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.glexy.dao.StatusAssetDao;
import com.lawencon.glexy.model.StatusAsset;
import com.lawencon.glexy.service.StatusAssetService;

@Service
public class StatusAssetServiceImpl extends BaseServiceImpl implements StatusAssetService {

	@Autowired
	private StatusAssetDao statusAssetDao;

	@Override
	public StatusAsset saveOrUpdate(StatusAsset data) throws Exception {
		try {
			if (data.getId() != null) {
				StatusAsset statusAsset = findById(data.getId());
				data.setCodeStatusAsset(statusAsset.getCodeStatusAsset());
				data.setCreatedAt(statusAsset.getCreatedAt());
				data.setCreatedBy(statusAsset.getCreatedBy());
				data.setUpdatedBy("1");
				data.setVersion(statusAsset.getVersion());
			} else {
				data.setCreatedBy("3");
			}

			begin();
			data = statusAssetDao.saveOrUpdate(data);
			commit();
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new Exception(e);
		}
	}

	@Override
	public boolean removeById(String id) throws Exception {
		boolean result = false;
		try {
			begin();
			result = statusAssetDao.removeById(id);
			commit();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new Exception(e);
		}
	}

	@Override
	public StatusAsset findById(String id) throws Exception {
		return statusAssetDao.findById(id);
	}

	@Override
	public List<StatusAsset> findAll() throws Exception {
		return statusAssetDao.findAll();
	}

	@Override
	public StatusAsset findByCode(String code) throws Exception {
		return statusAssetDao.findByCode(code);
	}

}
