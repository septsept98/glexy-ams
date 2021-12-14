package com.lawencon.glexy.dao;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.lawencon.base.BaseDaoImpl;
import com.lawencon.glexy.model.StatusTransaction;

@Repository
public class StatusTransactionDaoImpl extends BaseDaoImpl<StatusTransaction> implements StatusTransactionDao{

	@Override
	public List<StatusTransaction> findAll() throws Exception {
		return getAll();
	}

	@Override
	public StatusTransaction findById(String id) throws Exception {
		StatusTransaction result = new StatusTransaction();
		try {
			result = getById(id);
		} catch (NoResultException e) {
			throw new NoResultException("Status Transaction not found");
		}
		return result;
	}

	@Override
	public StatusTransaction saveOrUpdate(StatusTransaction data) throws Exception {
		return save(data);
	}

	@Override
	public boolean removeById(String id) throws Exception {
		return deleteById(id);
	}

}
