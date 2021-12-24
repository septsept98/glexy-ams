package com.lawencon.glexy.dao;

import java.util.List;

import com.lawencon.glexy.model.Transactions;

public interface TransactionDao {

	List<Transactions> findAll() throws Exception;

	Transactions findById(String id) throws Exception;

	Transactions saveOrUpdate(Transactions data) throws Exception;
	
	List<Transactions> findByEmployeeId(String id) throws Exception;
	
	List<Transactions> findByUsersId(String id) throws Exception;
	
	List<Transactions> findByLocationId(String id) throws Exception;
}
