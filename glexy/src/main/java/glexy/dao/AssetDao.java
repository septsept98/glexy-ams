package glexy.dao;

import java.util.List;

import glexy.model.Asset;

public interface AssetDao {
	
	void insert(Asset data) throws Exception;
	
	void update(Asset data) throws Exception;

	Asset findById(String id) throws Exception;
	
	List<Asset> findAll() throws Exception;
	
	

}
