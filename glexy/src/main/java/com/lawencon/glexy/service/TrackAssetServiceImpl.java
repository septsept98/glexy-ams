package com.lawencon.glexy.service;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.glexy.dao.TrackAssetDao;
import com.lawencon.glexy.model.TrackAsset;

public class TrackAssetServiceImpl extends BaseServiceImpl implements TrackAssetService {
	
	@Autowired
	private TrackAssetDao trackAssetDao;

	@Override
	public List<TrackAsset> findAll() throws Exception {
		return trackAssetDao.findAll();
	}

	@Override
	public TrackAsset findById(String id) throws Exception {
		TrackAsset result = new TrackAsset();
		try {
			result = trackAssetDao.findById(id);
		} catch (NoResultException e) {
			e.printStackTrace();
			throw new NoResultException("Status Transaction not found");
		}
		return result;
	}

	@Override
	public TrackAsset saveOrUpdate(TrackAsset data) throws Exception {
		try {
			begin();
			trackAssetDao.saveOrUpdate(data);
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new Exception(e);
		}
		return data;
	}

}
