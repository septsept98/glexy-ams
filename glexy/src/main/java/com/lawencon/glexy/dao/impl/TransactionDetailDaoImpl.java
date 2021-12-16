package com.lawencon.glexy.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.lawencon.base.BaseDaoImpl;
import com.lawencon.glexy.dao.TransactionDetailDao;
import com.lawencon.glexy.model.TransactionDetail;

@Repository
public class TransactionDetailDaoImpl extends BaseDaoImpl<TransactionDetail> implements TransactionDetailDao {

	@Override
	public List<TransactionDetail> findAll() throws Exception {
		return getAll();
	}

	@Override
	public TransactionDetail findById(String id) throws Exception {
		TransactionDetail result = new TransactionDetail();
		try {
			result = getById(id);
		} catch (NoResultException e) {
			throw new NoResultException("Transaction Detail not found");
		}
		return result;
	}

	@Override
	public TransactionDetail saveOrUpdate(TransactionDetail data) throws Exception {
		return save(data);
	}

	@Override
	public List<TransactionDetail> findByTr(String id) throws Exception {
		List<TransactionDetail> listResult = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("Select id ");
		sql.append("FROM transaction_details ");
		sql.append("WHERE transaction_id=:id");
		List<?> result = createNativeQuery(sql.toString())
				.setParameter("id", id)
				.getResultList();

		result.forEach(rs -> {
			TransactionDetail transactionDetail = new TransactionDetail();
			transactionDetail.setId(rs.toString());
			transactionDetail = getById(transactionDetail.getId());
			
			listResult.add(transactionDetail);
		});
		return listResult;
	}
	
}
