package com.lawencon.glexy.service;

import java.util.List;

import com.lawencon.glexy.model.StatusTransaction;

public interface StatusTransactionService {

	StatusTransaction saveOrUpdate(StatusTransaction data) throws Exception;
	
	boolean removeById(String id) throws Exception;
	
	StatusTransaction findById(String id) throws Exception;
	
	List<StatusTransaction> findAll() throws Exception;
}
