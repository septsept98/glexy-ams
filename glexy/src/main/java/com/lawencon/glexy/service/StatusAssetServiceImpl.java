package com.lawencon.glexy.service;

import java.util.List;

import javax.persistence.NoResultException;

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
				data.setUpdatedBy("1");
				data.setVersion(statusAsset.getVersion());
			} else {
				data.setCreatedBy("3");
			}

			begin();
			data = statusAssetDao.saveOrUpdate(data);
			commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new Exception(e);
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
			throw new Exception(e);
		}
		return result;
	}

	@Override
	public StatusAsset findById(String id) throws Exception {
		StatusAsset result = new StatusAsset();
		try {
			result = statusAssetDao.findById(id);
		} catch (NoResultException e) {
			throw new NoResultException("Status Asset not found");
		}
		return result;
	}

	@Override
	public List<StatusAsset> findAll() throws Exception {
		return statusAssetDao.findAll();
	}

}
