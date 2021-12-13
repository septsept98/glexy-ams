package com.lawencon.glexy.service;

import java.util.List;

import com.lawencon.glexy.model.StatusTransaction;

public interface StatusTransactionService {

	List<StatusTransaction> findAll() throws Exception;
}
