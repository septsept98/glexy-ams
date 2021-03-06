package com.lawencon.glexy.dao;

import java.util.List;

import com.lawencon.glexy.model.TransactionDetail;


public interface TransactionDetailDao {

	List<TransactionDetail> findAll() throws Exception;

	List<TransactionDetail> findAllOutDate() throws Exception;
	
	List<TransactionDetail> findAllNotCheckIn() throws Exception;
	
	List<TransactionDetail> findAllCheckIn() throws Exception;

	TransactionDetail findById(String id) throws Exception;
	
	List<TransactionDetail> findByTr(String id) throws Exception;
	
	List<TransactionDetail> findByTrNotCheckIn(String id) throws Exception;
	
	List<TransactionDetail> findByTrCheckIn(String id) throws Exception;

	TransactionDetail saveOrUpdate(TransactionDetail data) throws Exception;
	
	List<TransactionDetail> expDurationAssign() throws Exception;
	
	List<TransactionDetail> findByAssetId(String id) throws Exception;
	
	List<TransactionDetail> findByStatusAssetId(String id) throws Exception;
	
	List<TransactionDetail> findByStatusTransactionId(String id) throws Exception;

}
