package com.lawencon.glexy.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import org.springframework.stereotype.Repository;

import com.lawencon.base.BaseDaoImpl;
import com.lawencon.glexy.dao.StatusAssetDao;
import com.lawencon.glexy.model.StatusAsset;

@Repository
public class StatusAssetDaoImpl extends BaseDaoImpl<StatusAsset> implements StatusAssetDao {

	@Override
	public List<StatusAsset> findAll() throws Exception {
		return getAll();
	}

	@Override
	public StatusAsset findById(String id) throws Exception {
		return getById(id);
	}

	@Override
	public StatusAsset saveOrUpdate(StatusAsset data) throws Exception {
		data = save(data);
		return data;
	}

	@Override
	public boolean removeById(String id) throws Exception {
		return deleteById(id);
	}

	@Override
	public StatusAsset findByCode(String code) throws Exception {
		StatusAsset statusAsset = new StatusAsset();
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("Select id ");
			sql.append("FROM status_assets ");
			sql.append("WHERE code_status_asset=:code");
			
			Object result = createNativeQuery(sql.toString())
					.setParameter("code", code)
					.getSingleResult();	
			if(result != null) {
				statusAsset.setId(result.toString());
				statusAsset = getById(statusAsset.getId());
			}

		}  catch (NoResultException e) {
			e.printStackTrace();
		} catch (NonUniqueResultException e) {
			e.printStackTrace();
		}
		return statusAsset;
	}

	@Override
	public List<StatusAsset> findByName(String name) throws Exception {
		List<StatusAsset> listStatusAssets = new ArrayList<>();
			StringBuilder sql = new StringBuilder();
			sql.append("Select id ");
			sql.append("FROM status_assets ");
			sql.append("WHERE name_status_asset LIKE %:name% OR code_status_asset LIKE %:name%");
			
			List<?> result = createNativeQuery(sql.toString())
					.setParameter("name", name)
					.getResultList();	
			result.forEach(rs -> {
				StatusAsset statusAsset = new StatusAsset();
				statusAsset.setId(rs.toString());
				statusAsset = getById(statusAsset.getId());	
				
				listStatusAssets.add(statusAsset);
			});
			
		return listStatusAssets;
	}
	
}
