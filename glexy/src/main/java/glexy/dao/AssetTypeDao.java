package glexy.dao;

import java.util.List;

import glexy.model.AssetType;

public interface AssetTypeDao {
	
	void insert(AssetType data) throws Exception;

	void update(AssetType data) throws Exception;

	AssetType findById(String id) throws Exception;
	
	List<AssetType> findAll() throws Exception;
	

}
