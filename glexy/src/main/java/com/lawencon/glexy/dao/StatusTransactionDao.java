package com.lawencon.glexy.dao;

import java.util.List;

import com.lawencon.glexy.model.StatusTransaction;


public interface StatusTransactionDao {

	List<StatusTransaction> findAll() throws Exception;

	StatusTransaction findById(String id) throws Exception;

	StatusTransaction saveOrUpdate(StatusTransaction data) throws Exception;

	boolean deleteById(String id) throws Exception;
}
