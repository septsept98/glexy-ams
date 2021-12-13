package glexy.dao;

import java.util.List;

import com.lawencon.base.BaseDaoImpl;

import glexy.model.AssetType;

public class AssetTypeDaoImpl extends BaseDaoImpl<AssetType> implements AssetTypeDao{

	@Override
	public void insert(AssetType data) throws Exception {
		save(data);		
	}

	@Override
	public void update(AssetType data) throws Exception {
		save(data);		
	}

	@Override
	public AssetType findById(String id) throws Exception {
		return getById(id);
	}

	@Override
	public List<AssetType> findAll() throws Exception {
		return getAll();
	}
	

}
