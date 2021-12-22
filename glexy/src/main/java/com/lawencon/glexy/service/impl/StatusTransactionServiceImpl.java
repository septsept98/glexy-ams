package com.lawencon.glexy.service.impl;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.glexy.dao.StatusTransactionDao;
import com.lawencon.glexy.dao.TransactionDetailDao;
import com.lawencon.glexy.exception.ValidationGlexyException;
import com.lawencon.glexy.model.StatusAsset;
import com.lawencon.glexy.model.StatusTransaction;
import com.lawencon.glexy.model.TransactionDetail;
import com.lawencon.glexy.service.StatusAssetService;
import com.lawencon.glexy.service.StatusTransactionService;

@Service
public class StatusTransactionServiceImpl extends BaseServiceImpl implements StatusTransactionService {

	@Autowired
	private StatusTransactionDao statusTransactionDao;

	@Autowired
	private StatusAssetService statusAssetService;
	
	@Autowired
	private TransactionDetailDao transactionDetailDao;

	@Override
	public List<StatusTransaction> findAll() throws Exception {
		return statusTransactionDao.findAll();
	}

	@Override
	public StatusTransaction saveOrUpdate(StatusTransaction data) throws Exception {
		try {
			if (data.getId() != null) {
				validationUpdate(data);
				StatusTransaction statusTr = findById(data.getId());
				data.setCodeStatusTr(statusTr.getCodeStatusTr());
				data.setCreatedAt(statusTr.getCreatedAt());
				data.setCreatedBy(statusTr.getCreatedBy());
				data.setUpdatedBy("1");
				data.setVersion(statusTr.getVersion());
			} else {
				validationSave(data);
				data.setCreatedBy("3");
				data.setCodeStatusTr(generateCodeSTR());
			}

			StatusAsset statusAsset = statusAssetService.findById(data.getStatusAssetId().getId());
			if (statusAsset != null) {
				data.setStatusAssetId(statusAsset);

				begin();
				data = statusTransactionDao.saveOrUpdate(data);
				commit();
			}
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new Exception(e);
		}
	}

	@Override
	public boolean removeById(String id) throws Exception {
		boolean result = false;
		try {
			validationFk(id);
			begin();
			result = statusTransactionDao.removeById(id);
			commit();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new Exception(e);
		}
	}

	@Override
	public StatusTransaction findById(String id) throws Exception {
		StatusTransaction result = new StatusTransaction();
		try {
			result = statusTransactionDao.findById(id);
			return result;
		} catch (NoResultException e) {
			e.printStackTrace();
			throw new NoResultException("Status Transaction not found");
		}
	}

	@Override
	public String generateCodeSTR() throws Exception {
		String code = "STR";
		List<StatusTransaction> listStatusTransactions = findAll();
		int index = 1;
		if(listStatusTransactions != null) {
			index = listStatusTransactions.size();
		}
		String codeSTR = code + index;
		System.out.println(codeSTR);
		for(int i = 0; i<listStatusTransactions.size();i++) {
			System.out.println(listStatusTransactions.get(i).getCodeStatusTr());
			if(listStatusTransactions.get(i).getCodeStatusTr().equals(codeSTR)) {
				index++;
			}
			codeSTR = code + index;
		}
		
		return codeSTR ;
	}

	@Override
	public void validationFk(String id) throws Exception {
		
		List<TransactionDetail> dataTranscation = transactionDetailDao.findByStatusTransactionId(id);

		if (dataTranscation != null) {

			throw new ValidationGlexyException("Status Transaction in Use");
		}
		
	}

	@Override
	public void validationSave(StatusTransaction data) throws Exception {
		if(data.getCodeStatusTr() == null || data.getNameStatusTr() == null || data.getIsActive() == null) {
			throw new ValidationGlexyException("Data not Complete");
		}
		
	}

	@Override
	public void validationUpdate(StatusTransaction data) throws Exception {
		if (data.getId() != null) {
			StatusTransaction statusTransaction = findById(data.getId());
			if (statusTransaction == null) {
				throw new ValidationGlexyException("Data not Found");
			}
		} else {
			throw new ValidationGlexyException("Data not Found");
		}if(data.getCodeStatusTr() == null || data.getNameStatusTr() == null || data.getIsActive() == null) {
			throw new ValidationGlexyException("Data not Complete");
		}
		
	}

}
