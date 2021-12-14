package com.lawencon.glexy.dao;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.lawencon.base.BaseDaoImpl;
import com.lawencon.glexy.model.TrackAsset;

@Repository
public class TrackAssetDaoImpl extends BaseDaoImpl<TrackAsset> implements TrackAssetDao {

	@Override
	public List<TrackAsset> findAll() throws Exception {
		return getAll();
	}

	@Override
	public TrackAsset findById(String id) throws Exception {
		TrackAsset result = new TrackAsset();
		try {
			result = getById(id);
		} catch (NoResultException e) {
			throw new NoResultException("Track Asset not found");
		}
		return result;
	}

	@Override
	public TrackAsset saveOrUpdate(TrackAsset data) throws Exception {
		return save(data);
	}

}
