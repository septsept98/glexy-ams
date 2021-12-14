package com.lawencon.glexy.service;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.glexy.dao.TransactionDetailDao;
import com.lawencon.glexy.model.Asset;
import com.lawencon.glexy.model.StatusAsset;
import com.lawencon.glexy.model.StatusTransaction;
import com.lawencon.glexy.model.TrackAsset;
import com.lawencon.glexy.model.TransactionDetail;
import com.lawencon.glexy.model.Transactions;

@Service
public class TransactionDetailServiceImpl extends BaseServiceImpl implements TransactionDetailService {

	private TransactionDetailDao transactionDetailDao;
	private AssetService assetService;
	private TransactionService transactionService;
	private StatusTransactionService statusTransactionService;
	private TrackAssetService trackAssetService;
	
	public TransactionDetailServiceImpl(@Autowired TransactionDetailDao transactionDetailDao,
			AssetService assetService, StatusTransactionService statusTransactionService,
			TransactionService transactionService,
			TrackAssetService trackAssetService) {
		this.transactionDetailDao = transactionDetailDao;
		this.assetService = assetService;
		this.statusTransactionService = statusTransactionService;
		this.trackAssetService = trackAssetService;
		this.transactionService = transactionService;
	}

	@Override
	public List<TransactionDetail> findAll() throws Exception {
		return transactionDetailDao.findAll();
	}

	@Override
	public TransactionDetail findById(String id) throws Exception {
		TransactionDetail result = new TransactionDetail();
		try {
			result = transactionDetailDao.findById(id);
		} catch (NoResultException e) {
			e.printStackTrace();
			throw new NoResultException("Status Transaction not found");
		}
		return result;
	}

	@Override
	public TransactionDetail saveOrUpdate(TransactionDetail data) throws Exception {
		try {
			if(data.getId() != null) {
				TransactionDetail transactionDetail = transactionDetailDao.findById(data.getId());
				data.setCreatedBy(transactionDetail.getCreatedBy());
				data.setCreatedAt(transactionDetail.getCreatedAt());
				data.setUpdatedBy("1");
				data.setVersion(transactionDetail.getVersion());
				
				StatusTransaction statusTransaction = statusTransactionService.findById(data.getStatusTrCheckinId().getId());
				
				StatusAsset statusAsset = new StatusAsset();
				statusAsset.setId(statusTransaction.getStatusAssetId().getId());
				
				Asset asset = assetService.findById(data.getAssetId().getId());
				asset.setStatusAssetId(statusAsset);
				asset.setUpdatedBy("22");
				begin();
				assetService.saveOrUpdate(asset);
				commit();
				
				Transactions transactions = transactionService.findById(data.getTransactionId().getId());
				
				TrackAsset trackAsset = new TrackAsset();
				trackAsset.setCodeAsset(asset.getCode());
				trackAsset.setNameActivity(statusTransaction.getNameStatusTr());
				trackAsset.setDateActivity(LocalDate.now());
				trackAsset.setUserId("33");
				trackAsset.setTransactionCode(transactions.getCodeTransaction());
				trackAsset.setCreatedBy(data.getCreatedBy());
				trackAsset.setIsActive(data.getIsActive());
				begin();
				trackAssetService.saveOrUpdate(trackAsset);
				commit();
			}
			begin();
			data = transactionDetailDao.saveOrUpdate(data);
			commit();				
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new Exception(e);
		}
		return data;
	}
	
	
}
