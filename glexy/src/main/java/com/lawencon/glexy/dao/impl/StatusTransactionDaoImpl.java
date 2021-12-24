package com.lawencon.glexy.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.BaseDaoImpl;
import com.lawencon.glexy.dao.StatusTransactionDao;
import com.lawencon.glexy.model.StatusAsset;
import com.lawencon.glexy.model.StatusTransaction;

@Repository
public class StatusTransactionDaoImpl extends BaseDaoImpl<StatusTransaction> implements StatusTransactionDao {

	@Override
	public List<StatusTransaction> findAll() throws Exception {
		return getAll();
	}

	@Override
	public StatusTransaction findById(String id) throws Exception {
		return getById(id);
	}

	@Override
	public StatusTransaction saveOrUpdate(StatusTransaction data) throws Exception {
		return save(data);
	}

	@Override
	public boolean removeById(String id) throws Exception {
		return deleteById(id);
	}

	@Override
	public List<StatusTransaction> findByStatusAssetId(String id) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT status_asset_id FROM status_transactions ");
		sql.append("WHERE status_asset_id = :id");
		List<?> result = createNativeQuery(sql.toString()).setParameter("id", id).getResultList();

		List<StatusTransaction> resultStatusTansaction = new ArrayList<>();

		result.forEach(rs -> {

			StatusTransaction data = new StatusTransaction();

			StatusAsset statusAsset = new StatusAsset();
			statusAsset.setId(rs.toString());
			data.setStatusAssetId(statusAsset);
			resultStatusTansaction.add(data);
		});

		return resultStatusTansaction;
	}

}
