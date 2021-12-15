package com.lawencon.glexy.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.BaseDaoImpl;
import com.lawencon.glexy.model.Asset;

@Repository
public class AssetDaoImpl extends BaseDaoImpl<Asset> implements AssetDao{

	@Override
	public Asset saveOrUpdate(Asset data) throws Exception {
		return save(data);
	}

	@Override
	public boolean removeById(String id) throws Exception {
		return deleteById(id);
	}

	@Override
	public Asset findById(String id) throws Exception {
		return getById(id);
	}

	@Override
	public List<Asset> findAll() throws Exception {
		
		return getAll();
	}

	@Override
	public List<Asset> findByInvent(String idInvent) throws Exception {
		List<Asset> listResult = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("Select id ");
		sql.append("FROM assets ");
		sql.append("WHERE inventory_id=:idInvent");
		List<?> result = createNativeQuery(sql.toString())
				.setParameter("idInvent", idInvent)
				.getResultList();

		result.forEach(rs -> {
			Asset asset = new Asset();
			asset.setId(rs.toString());
			asset = getById(asset.getId());
			
			listResult.add(asset);
		});
		return listResult;
	}


	
	
	
}
