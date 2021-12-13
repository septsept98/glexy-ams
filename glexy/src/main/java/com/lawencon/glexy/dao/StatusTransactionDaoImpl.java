package com.lawencon.glexy.dao;

import java.util.List;

import com.lawencon.base.BaseDaoImpl;
import com.lawencon.glexy.model.StatusTransaction;

public class StatusTransactionDaoImpl extends BaseDaoImpl<StatusTransaction> implements StatusTransactionDao{

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
		save(data);
		return null;
	}

	@Override
	public boolean deleteById(String id) throws Exception {
		super.deleteById(id);
		return false;
	}

}
