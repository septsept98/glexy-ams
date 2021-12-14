package com.lawencon.glexy.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.glexy.dao.TransactionDao;
import com.lawencon.glexy.dto.transaction.InsertReqTransaction;
import com.lawencon.glexy.model.Asset;
import com.lawencon.glexy.model.StatusAsset;
import com.lawencon.glexy.model.TrackAsset;
import com.lawencon.glexy.model.TransactionDetail;
import com.lawencon.glexy.model.Transactions;

@Service
public class TransactionServiceImpl extends BaseServiceImpl implements TransactionService {

	private TransactionDao transactionDao;
	private TransactionDetailService transactionDetailService;
	private TrackAssetService trackAssetService;
	private AssetService assetService;
	private StatusAssetService statusAssetService;

	public TransactionServiceImpl(@Autowired TransactionDao transactionDao,
			TransactionDetailService transactionDetailService, TrackAssetService trackAssetService,
			AssetService assetService, StatusAssetService statusAssetService) {
		this.transactionDao = transactionDao;
		this.transactionDetailService = transactionDetailService;
		this.trackAssetService = trackAssetService;
		this.assetService = assetService;
		this.statusAssetService = statusAssetService;
	}

	@Override
	public List<Transactions> findAll() throws Exception {
		return transactionDao.findAll();
	}

	@Override
	public Transactions findById(String id) throws Exception {
		return transactionDao.findById(id);
	}

	@Override
	public InsertReqTransaction saveOrUpdate(InsertReqTransaction data) throws Exception {
		InsertReqTransaction result = new InsertReqTransaction();

		Transactions dataTransaction = data.getDataTransaction();
		dataTransaction.setCreatedBy("3");
		begin();
		dataTransaction = transactionDao.saveOrUpdate(dataTransaction);
		commit();

		List<TransactionDetail> listDataDetailTransaction = data.getDataDetailTransaction();

		for (int i = 0; i < listDataDetailTransaction.size(); i++) {
			TransactionDetail detailTr = listDataDetailTransaction.get(i);
			detailTr.setTransactionId(dataTransaction);
			detailTr.setCreatedBy(dataTransaction.getCreatedBy());
			detailTr.setIsActive(dataTransaction.getIsActive());

			StatusAsset statusAsset = statusAssetService.findById(detailTr.getStatusAssetCheckoutId().getId());
			detailTr.setStatusAssetCheckoutId(statusAsset);
			begin();
			transactionDetailService.saveOrUpdate(detailTr);
			commit();
			
			Asset asset = assetService.findById(detailTr.getAssetId().getId());
			if (asset.getId() != null) {
				asset.setStatusAssetId(statusAsset);
				begin();
				assetService.saveOrUpdate(asset);
				commit();
			}
			
			TrackAsset trackAsset = new TrackAsset();
			trackAsset.setCodeAsset(asset.getCode());
			trackAsset.setNameActivity(statusAsset.getNameStatusAsset());
			trackAsset.setDateActivity(LocalDate.now());
			trackAsset.setUserId("33");
			trackAsset.setTransactionCode(dataTransaction.getCodeTransaction());
			trackAsset.setCreatedBy(dataTransaction.getCreatedBy());
			trackAsset.setIsActive(dataTransaction.getIsActive());
			begin();
			trackAssetService.saveOrUpdate(trackAsset);
			commit();

		}
		return result;
	}

}
