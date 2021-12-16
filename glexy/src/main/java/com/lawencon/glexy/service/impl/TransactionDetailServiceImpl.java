package com.lawencon.glexy.service.impl;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.glexy.constant.StatusAssetEnum;
import com.lawencon.glexy.dao.AssetDao;
import com.lawencon.glexy.dao.TransactionDetailDao;
import com.lawencon.glexy.model.Asset;
import com.lawencon.glexy.model.Inventory;
import com.lawencon.glexy.model.StatusAsset;
import com.lawencon.glexy.model.StatusTransaction;
import com.lawencon.glexy.model.TrackAsset;
import com.lawencon.glexy.model.TransactionDetail;
import com.lawencon.glexy.model.Transactions;
import com.lawencon.glexy.service.InventoryService;
import com.lawencon.glexy.service.StatusTransactionService;
import com.lawencon.glexy.service.TrackAssetService;
import com.lawencon.glexy.service.TransactionDetailService;
import com.lawencon.glexy.service.TransactionService;

@Service
public class TransactionDetailServiceImpl extends BaseServiceImpl implements TransactionDetailService {

	@Autowired
	private TransactionDetailDao transactionDetailDao;

	@Autowired
	private AssetDao assetDao;
  
	private TransactionService transactionService;
	private StatusTransactionService statusTransactionService;
	private TrackAssetService trackAssetService;
	private InventoryService inventoryService;
	
	public TransactionDetailServiceImpl(
			InventoryService inventoryService,
			StatusTransactionService statusTransactionService,
			TrackAssetService trackAssetService) {
		this.inventoryService = inventoryService;
		this.statusTransactionService = statusTransactionService;
		this.trackAssetService = trackAssetService;
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
				
				Asset asset = assetDao.findById(data.getAssetId().getId());
				asset.setStatusAssetId(statusAsset);
				asset.setUpdatedBy("22");
				begin();
				asset = assetDao.saveOrUpdate(asset);
				
				Inventory inventory = inventoryService.findById(asset.getInventoryId().getId());
				if(asset.getStatusAssetId().getCodeStatusAsset() == StatusAssetEnum.DEPLOY.getCode()) {
					int stockInventory = inventory.getLatestStock();
					int latestStock = stockInventory - 1;
					
					inventory.setUpdatedBy("22");
					inventory.setLatestStock(latestStock);
					
					inventory = inventoryService.saveOrUpdate(inventory);
				}
				
				Transactions transactions = transactionService.findById(data.getTransactionId().getId());
				
				TrackAsset trackAsset = new TrackAsset();
				trackAsset.setCodeAsset(asset.getCode());
				trackAsset.setNameActivity(statusTransaction.getNameStatusTr());
				trackAsset.setDateActivity(LocalDate.now());
				trackAsset.setUserId("33");
				trackAsset.setTransactionCode(transactions.getCodeTransaction());
				trackAsset.setCreatedBy(data.getCreatedBy());
				trackAsset.setIsActive(data.getIsActive());
				trackAssetService.saveOrUpdate(trackAsset);
				
				data = transactionDetailDao.saveOrUpdate(data);
				commit();
			} else {
				data = transactionDetailDao.saveOrUpdate(data);
			}
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new Exception(e);
		}
		return data;
	}

	@Override
	public List<TransactionDetail> findByTr(String id) throws Exception {
		return transactionDetailDao.findByTr(id);
	}
	
	
}
