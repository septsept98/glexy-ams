package com.lawencon.glexy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.glexy.dao.StatusTransactionDao;
import com.lawencon.glexy.model.StatusTransaction;

@Service
public class StatusTransactionServiceImpl extends BaseServiceImpl implements StatusTransactionService {

	@Autowired
	private StatusTransactionDao statusTransactionDao;

	@Override
	public List<StatusTransaction> findAll() throws Exception {
		return statusTransactionDao.findAll();
	}
	
	
}
