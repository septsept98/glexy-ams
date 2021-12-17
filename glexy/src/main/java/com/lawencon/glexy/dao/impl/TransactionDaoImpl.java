package com.lawencon.glexy.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.lawencon.base.BaseDaoImpl;
import com.lawencon.glexy.dao.TransactionDao;
import com.lawencon.glexy.model.Transactions;

@Repository
public class TransactionDaoImpl extends BaseDaoImpl<Transactions> implements TransactionDao {

	@Override
	public List<Transactions> findAll() throws Exception {
		return getAll();
	}

	@Override
	public Transactions findById(String id) throws Exception {
		Transactions result = new Transactions();
		try {
			result = getById(id);
		} catch (NoResultException e) {
			throw new NoResultException("Transaction not found");
		}
		return result;
	}

	@Override
	public Transactions saveOrUpdate(Transactions data) throws Exception {
		return save(data);
	}
}