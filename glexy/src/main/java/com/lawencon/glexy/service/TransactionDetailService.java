package com.lawencon.glexy.service;

import java.util.List;

import com.lawencon.glexy.model.StatusTransaction;
import com.lawencon.glexy.model.TransactionDetail;

public interface TransactionDetailService {

	List<TransactionDetail> findAll() throws Exception;

	TransactionDetail findById(String id) throws Exception;

	List<TransactionDetail> findByTr(String id) throws Exception;

	TransactionDetail saveOrUpdate(TransactionDetail data) throws Exception;
	
	List<TransactionDetail> expDurationAssign() throws Exception;
	
	void validationSave(TransactionDetail data) throws Exception;

}
