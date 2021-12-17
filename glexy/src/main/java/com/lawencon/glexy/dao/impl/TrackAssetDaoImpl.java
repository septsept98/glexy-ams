package com.lawencon.glexy.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.lawencon.base.BaseDaoImpl;
import com.lawencon.glexy.dao.TrackAssetDao;
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

	@Override
	public List<TrackAsset> findByAssetTr(String assetCode, String trCode) throws Exception {
		List<TrackAsset> listResult = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("Select id ");
		sql.append("FROM track_assets ");
		sql.append("WHERE code_asset=:assetCode ");
		sql.append("AND transaction_code=:trCode ");
		List<?> result = createNativeQuery(sql.toString())
				.setParameter("assetCode", assetCode)
				.setParameter("trCode", trCode)
				.getResultList();

		result.forEach(rs -> {
			TrackAsset trackAsset = new TrackAsset();
			trackAsset.setId(rs.toString());
			trackAsset = getById(trackAsset.getId());
			
			listResult.add(trackAsset);
		});
		return listResult;
	}

	@Override
	public List<TrackAsset> findByAsset(String assetCode) throws Exception {
		List<TrackAsset> listResult = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("Select id ");
		sql.append("FROM track_assets ");
		sql.append("WHERE code_asset=:assetCode");
		List<?> result = createNativeQuery(sql.toString())
				.setParameter("assetCode", assetCode)
				.getResultList();

		result.forEach(rs -> {
			TrackAsset trackAsset = new TrackAsset();
			trackAsset.setId(rs.toString());
			trackAsset = getById(trackAsset.getId());
			
			listResult.add(trackAsset);
		});
		return listResult;
	}

}
