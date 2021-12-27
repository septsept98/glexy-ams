package com.lawencon.glexy.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import org.springframework.stereotype.Repository;

import com.lawencon.base.BaseDaoImpl;
import com.lawencon.glexy.dao.AssetTypeDao;
import com.lawencon.glexy.model.AssetType;

@Repository
public class AssetTypeDaoImpl extends BaseDaoImpl<AssetType> implements AssetTypeDao{

	@Override
	public AssetType saveOrUpdate(AssetType data) throws Exception {
		return save(data);
	}

	@Override
	public boolean removeById(String id) throws Exception {
		return deleteById(id);
	}

	@Override
	public AssetType findById(String id) throws Exception {
		return getById(id);
	}

	@Override
	public List<AssetType> findAll() throws Exception {
		return getAll();
	}

	@Override
	public AssetType findByCode(String code) throws Exception {
		AssetType assetType = new AssetType();
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("Select id ");
			sql.append("FROM asset_types ");
			sql.append("WHERE code=:code");
			
			Object result = createNativeQuery(sql.toString())
					.setParameter("code", code)
					.getSingleResult();	
			if(result != null) {
				assetType.setId(result.toString());
				assetType = getById(assetType.getId());
			}

		}  catch (NoResultException e) {
			e.printStackTrace();
		} catch (NonUniqueResultException e) {
			e.printStackTrace();
		}
		return assetType;
	}

	@Override
	public List<AssetType> searchByNameCode(String search) throws Exception {
		List<AssetType> listResult = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("Select id ");
		sql.append("FROM asset_types ");
		sql.append("WHERE code LIKE '%" + search + "%' OR names LIKE '%" + search + "%' ");

		List<?> result = createNativeQuery(sql.toString()).getResultList();

		result.forEach(rs -> {
			AssetType assetType = new AssetType();
			assetType.setId(rs.toString());
			assetType = getById(assetType.getId());

			listResult.add(assetType);
		});

		return listResult;
	}
	
	
	

}
