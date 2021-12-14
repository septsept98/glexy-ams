package com.lawencon.glexy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.glexy.dao.StatusTransactionDao;
import com.lawencon.glexy.model.StatusAsset;
import com.lawencon.glexy.model.StatusTransaction;

@Service
public class StatusTransactionServiceImpl extends BaseServiceImpl implements StatusTransactionService {

	@Autowired
	private StatusTransactionDao statusTransactionDao;
	
	@Autowired
	private StatusAssetService statusAssetService;

	@Override
	public List<StatusTransaction> findAll() throws Exception {
		return statusTransactionDao.findAll();
	}

	@Override
	public StatusTransaction saveOrUpdate(StatusTransaction data) throws Exception {
		try {
			if(data.getId() != null) {
				StatusTransaction statusTr = findById(data.getId());
				data.setCodeStatusTr(statusTr.getCodeStatusTr());
				data.setCreatedAt(statusTr.getCreatedAt());
				data.setCreatedBy(statusTr.getCreatedBy());
				data.setVersion(statusTr.getVersion());
			}
			
			StatusAsset statusAsset = statusAssetService.findById(data.getStatusAssetId().getId());
			if(statusAsset != null) {
				data.setStatusAssetId(statusAsset);

				begin();
				data = statusTransactionDao.saveOrUpdate(data);
				commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
		}
		return data;
	}

	@Override
	public boolean removeById(String id) throws Exception {
		boolean result = false;
		try {
			begin();
			result = statusTransactionDao.removeById(id);
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
		}
		return result;
	}

	@Override
	public StatusTransaction findById(String id) throws Exception {
		return statusTransactionDao.findById(id);
	}
	
	
}
