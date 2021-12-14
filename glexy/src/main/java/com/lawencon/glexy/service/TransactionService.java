package com.lawencon.glexy.service;

import java.util.List;

import com.lawencon.glexy.dto.transaction.InsertReqTransaction;
import com.lawencon.glexy.model.Transactions;

public interface TransactionService {

	List<Transactions> findAll() throws Exception;

	Transactions findById(String id) throws Exception;

	InsertReqTransaction saveOrUpdate(InsertReqTransaction data) throws Exception;

}
