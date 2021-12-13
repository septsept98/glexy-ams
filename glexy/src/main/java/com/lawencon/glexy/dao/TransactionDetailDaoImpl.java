package com.lawencon.glexy.dao;

import java.util.List;

import com.lawencon.base.BaseDaoImpl;
import com.lawencon.glexy.model.TransactionDetail;

public class TransactionDetailDaoImpl extends BaseDaoImpl<TransactionDetail> implements TransactionDetailDao {

	@Override
	public List<TransactionDetail> findAll() throws Exception {
		return getAll();
	}

	@Override
	public TransactionDetail findById(String id) throws Exception {
		return getById(id);
	}

	@Override
	public TransactionDetail saveOrUpdate(TransactionDetail data) throws Exception {
		save(data);
		return null;
	}

	
}
