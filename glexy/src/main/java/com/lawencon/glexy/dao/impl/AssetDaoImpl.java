package com.lawencon.glexy.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.BaseDaoImpl;
import com.lawencon.glexy.dao.AssetDao;
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
  
	public List<Asset> findByBrandId(String brandId) throws Exception {
		List<Asset> resultList = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ass ");
		sb.append("FROM Asset as ass ");
		sb.append("INNER JOIN FETCH ass.invoiceId ");
		sb.append("INNER JOIN FETCH ass.companyId as com ");
		sb.append("INNER JOIN FETCH ass.assetImg ");
		sb.append("INNER JOIN FETCH ass.assetTypeId ");
		sb.append("INNER JOIN FETCH ass.inventoryId ");
		sb.append("INNER JOIN FETCH ass.statusAssetId ");
		sb.append("INNER JOIN FETCH ass.brandId as b ");
		sb.append("WHERE b.id = :branId ");
		
		String sql = sb.toString();
		resultList = createQuery(sql, Asset.class)
				.setParameter("branId", brandId)
				.getResultList();
		return resultList;
	}
	
	@Override
	public List<Asset> findByCompanyId(String companyId) throws Exception {
		List<Asset> resultList = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ass ");
		sb.append("FROM Asset as ass ");
		sb.append("INNER JOIN FETCH ass.invoiceId ");
		sb.append("INNER JOIN FETCH ass.companyId as com ");
		sb.append("INNER JOIN FETCH ass.assetImg ");
		sb.append("INNER JOIN FETCH ass.assetTypeId ");
		sb.append("INNER JOIN FETCH ass.inventoryId ");
		sb.append("INNER JOIN FETCH ass.statusAssetId ");
		sb.append("INNER JOIN FETCH ass.brandId as b ");
		sb.append("WHERE com.id = :comId ");
		
		String sql = sb.toString();
		resultList = createQuery(sql, Asset.class)
				.setParameter("comId", companyId)
				.getResultList();

		return resultList;
	}

}
