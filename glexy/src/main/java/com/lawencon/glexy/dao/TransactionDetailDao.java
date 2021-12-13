package com.lawencon.glexy.dao;

import java.util.List;

import com.lawencon.glexy.model.TransactionDetail;


public interface TransactionDetailDao {

	List<TransactionDetail> findAll() throws Exception;

	TransactionDetail findById(String id) throws Exception;

	TransactionDetail saveOrUpdate(TransactionDetail data) throws Exception;

}
