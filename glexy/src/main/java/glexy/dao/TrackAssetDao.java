package glexy.dao;

import java.util.List;

import glexy.model.TrackAsset;


public interface TrackAssetDao {

	List<TrackAsset> findAll() throws Exception;

	TrackAsset findById(String id) throws Exception;

	TrackAsset saveOrUpdate(TrackAsset data) throws Exception;
}
