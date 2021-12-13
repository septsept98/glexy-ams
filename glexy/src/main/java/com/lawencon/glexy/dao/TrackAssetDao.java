package com.lawencon.glexy.dao;

import java.util.List;

import com.lawencon.glexy.model.TrackAsset;


public interface TrackAssetDao {

	List<TrackAsset> findAll() throws Exception;

	TrackAsset findById(String id) throws Exception;

	TrackAsset saveOrUpdate(TrackAsset data) throws Exception;
}
