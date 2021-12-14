package com.lawencon.glexy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.glexy.dao.StatusAssetDao;
import com.lawencon.glexy.model.StatusAsset;

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
				data.setVersion(statusAsset.getVersion());
			}

			begin();
			data = statusAssetDao.saveOrUpdate(data);
			commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
		}
		return data;
	}

	@Override
	public boolean removeById(String id) throws Exception {
		boolean result = false;
		try {
			begin();
			result = statusAssetDao.removeById(id);
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
		}
		return result;
	}

	@Override
	public StatusAsset findById(String id) throws Exception {
		return statusAssetDao.findById(id);
	}

	@Override
	public List<StatusAsset> findAll() throws Exception {
		return statusAssetDao.findAll();
	}

}
