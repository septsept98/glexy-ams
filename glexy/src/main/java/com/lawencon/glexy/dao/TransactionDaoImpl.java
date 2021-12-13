package com.lawencon.glexy.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.BaseDaoImpl;
import com.lawencon.glexy.model.Transactions;

@Repository
public class TransactionDaoImpl extends BaseDaoImpl<Transactions> implements TransactionDao {

	@Override
	public List<Transactions> findAll() throws Exception {
		return getAll();
	}

	@Override
	public Transactions findById(String id) throws Exception {
		return getById(id);
	}

	@Override
	public Transactions saveOrUpdate(Transactions data) throws Exception {
		return save(data);
	}
}
