package com.lawencon.glexy.service;

import java.util.List;

import com.lawencon.glexy.model.TrackAsset;

public interface TrackAssetService {

	List<TrackAsset> findAll() throws Exception;

	TrackAsset findById(String id) throws Exception;

	TrackAsset saveOrUpdate(TrackAsset data) throws Exception;
}
