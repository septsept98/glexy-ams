package com.lawencon.glexy.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.lawencon.base.BaseDaoImpl;
import com.lawencon.glexy.dao.TransactionDetailDao;
import com.lawencon.glexy.model.Asset;
import com.lawencon.glexy.model.StatusAsset;
import com.lawencon.glexy.model.StatusTransaction;
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
		List<?> result = createNativeQuery(sql.toString()).setParameter("id", id).getResultList();

		result.forEach(rs -> {
			TransactionDetail transactionDetail = new TransactionDetail();
			transactionDetail.setId(rs.toString());
			transactionDetail = getById(transactionDetail.getId());

			listResult.add(transactionDetail);
		});
		return listResult;
	}

	@Override
	public List<TransactionDetail> expDurationAssign() throws Exception {
		List<TransactionDetail> listResult = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id ");
		sql.append("FROM transaction_details ");
		sql.append("WHERE (date_part('day', duration_date) - date_part('day', now())) <= 7 AND status_email = false");
		List<?> result = createNativeQuery(sql.toString()).getResultList();

		result.forEach(res -> {
			TransactionDetail transactionDetail = new TransactionDetail();
			transactionDetail.setId(res.toString());
			transactionDetail = getById(transactionDetail.getId());

			listResult.add(transactionDetail);
		});
		return listResult;
	}

	@Override
	public List<TransactionDetail> findByAssetId(String id) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT asset_id FROM transaction_details ");
		sql.append("WHERE asset_id = :id");
		List<?> result = createNativeQuery(sql.toString()).setParameter("id", id).getResultList();

		List<TransactionDetail> resultTransactionDetail = new ArrayList<>();

		result.forEach(rs -> {

			TransactionDetail data = new TransactionDetail();

			Asset asset = new Asset();
			asset.setId(rs.toString());
			data.setAssetId(asset);
			resultTransactionDetail.add(data);
		});

		return resultTransactionDetail;
	}

	@Override
	public List<TransactionDetail> findAllOutDate() throws Exception {
		List<TransactionDetail> listResult = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("Select id ");
		sql.append("FROM transaction_details ");
		sql.append("WHERE date_checkin > duration_date ");
		sql.append("ORDER BY duration_date ASC");
		List<?> result = createNativeQuery(sql.toString()).getResultList();

		result.forEach(rs -> {
			TransactionDetail transactionDetail = new TransactionDetail();
			transactionDetail.setId(rs.toString());
			transactionDetail = getById(transactionDetail.getId());

			listResult.add(transactionDetail);
		});
		return listResult;
	}

	public List<TransactionDetail> findByStatusAssetId(String id) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT status_asset_checkout_id FROM transaction_details ");
		sql.append("WHERE status_asset_checkout_id= :id");
		List<?> result = createNativeQuery(sql.toString()).setParameter("id", id).getResultList();

		List<TransactionDetail> resultTransactionDetail = new ArrayList<>();

		result.forEach(rs -> {

			TransactionDetail data = new TransactionDetail();

			StatusAsset statusAsset = new StatusAsset();
			statusAsset.setId(rs.toString());
			data.setStatusAssetCheckoutId(statusAsset);
			resultTransactionDetail.add(data);
		});

		return resultTransactionDetail;
	}

	@Override
	public List<TransactionDetail> findByStatusTransactionId(String id) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT status_tr_checkin_id FROM transaction_details ");
		sql.append("WHERE status_tr_checkin_id = :id");
		List<?> result = createNativeQuery(sql.toString()).setParameter("id", id).getResultList();

		List<TransactionDetail> resultTransactionDetail = new ArrayList<>();

		result.forEach(rs -> {

			TransactionDetail data = new TransactionDetail();

			StatusTransaction statusTransaction = new StatusTransaction();
			statusTransaction.setId(rs.toString());
			data.setStatusTrCheckinId(statusTransaction);
			resultTransactionDetail.add(data);
		});

		return resultTransactionDetail;
	}

	@Override
	public List<TransactionDetail> findByTrNotCheckIn(String id) throws Exception {
		List<TransactionDetail> listResult = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("Select td.id ");
		sql.append("FROM transaction_details td ");
		sql.append("INNER JOIN transactions t ON t.id = td.transaction_id ");
		sql.append("WHERE td.date_checkin ISNULL AND td.status_tr_checkin_id ISNULL AND ");
		sql.append("t.id=:id");
		List<?> result = createNativeQuery(sql.toString()).setParameter("id", id).getResultList();

		result.forEach(rs -> {
			TransactionDetail transactionDetail = new TransactionDetail();
			transactionDetail.setId(rs.toString());
			transactionDetail = getById(transactionDetail.getId());

			listResult.add(transactionDetail);
		});
		return listResult;
	}

}
