package com.lawencon.glexy.service;

import java.util.List;

import com.lawencon.glexy.dto.transaction.InsertReqDataAssetTransactionDto;
import com.lawencon.glexy.dto.transaction.InsertReqTransactionDto;
import com.lawencon.glexy.model.Asset;
import com.lawencon.glexy.model.Transactions;

public interface TransactionService {

	List<Transactions> findAll() throws Exception;

	List<Transactions> findAllNotCheckIn() throws Exception;

	List<Transactions> findAllCheckIn() throws Exception;

	Transactions findById(String id) throws Exception;
	
	List<Asset> findAssetDetail(InsertReqDataAssetTransactionDto data) throws Exception;

	InsertReqTransactionDto saveOrUpdate(InsertReqTransactionDto data) throws Exception;
	
	void validationSave(Transactions data) throws Exception;
	
	String generateCode() throws Exception; 

}
