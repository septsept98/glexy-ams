package com.lawencon.glexy.dao;

import java.util.List;

import com.lawencon.glexy.model.TrackAsset;


public interface TrackAssetDao {

	List<TrackAsset> findAll() throws Exception;

	TrackAsset findById(String id) throws Exception;

	List<TrackAsset> findByAsset(String assetCode) throws Exception;
	
	List<TrackAsset> findByAssetTr(String assetCode, String trCode) throws Exception;

	TrackAsset saveOrUpdate(TrackAsset data) throws Exception;
}
