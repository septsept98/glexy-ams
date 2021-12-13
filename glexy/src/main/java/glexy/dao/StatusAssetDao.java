package glexy.dao;

import java.util.List;

import glexy.model.StatusAsset;

public interface StatusAssetDao {

	List<StatusAsset> findAll() throws Exception;

	StatusAsset findById(String id) throws Exception;

	StatusAsset saveOrUpdate(StatusAsset data) throws Exception;

	boolean deleteById(String id) throws Exception;
	
}
