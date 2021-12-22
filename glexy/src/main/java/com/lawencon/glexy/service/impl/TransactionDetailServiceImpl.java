package com.lawencon.glexy.service.impl;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.glexy.constant.StatusAssetEnum;
import com.lawencon.glexy.dao.AssetDao;
import com.lawencon.glexy.dao.TransactionDetailDao;
import com.lawencon.glexy.email.EmailHandler;
import com.lawencon.glexy.helper.EmailHelper;
import com.lawencon.glexy.model.Asset;
import com.lawencon.glexy.model.Inventory;
import com.lawencon.glexy.model.TrackAsset;
import com.lawencon.glexy.model.TransactionDetail;
import com.lawencon.glexy.service.InventoryService;
import com.lawencon.glexy.service.TrackAssetService;
import com.lawencon.glexy.service.TransactionDetailService;

@Service
public class TransactionDetailServiceImpl extends BaseServiceImpl implements TransactionDetailService {

	@Autowired
	private TransactionDetailDao transactionDetailDao;

	@Autowired
	private AssetDao assetDao;

	@Autowired
	private TrackAssetService trackAssetService;

	@Autowired
	private InventoryService inventoryService;
	
	@Autowired
	private EmailHandler emailHandler;

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
			if (data.getId() != null) {
				TransactionDetail transactionDetail = transactionDetailDao.findById(data.getId());
				data.setCreatedBy(transactionDetail.getCreatedBy());
				data.setCreatedAt(transactionDetail.getCreatedAt());
				data.setUpdatedBy("1");
				data.setVersion(transactionDetail.getVersion());
				data.setTransactionId(transactionDetail.getTransactionId());
				data.setDateCheckin(transactionDetail.getDateCheckin());
				data.setDurationDate(transactionDetail.getDurationDate());
				data.setStatusAssetCheckoutId(transactionDetail.getStatusAssetCheckoutId());
				data.setDateCheckin(LocalDate.now());
				data.setAssetId(transactionDetail.getAssetId());
				data.setStatusTrCheckinId(data.getStatusTrCheckinId());

				begin();
				data = transactionDetailDao.saveOrUpdate(data);

				Asset asset = data.getAssetId();
				asset.setStatusAssetId(data.getStatusTrCheckinId().getStatusAssetId());
				asset.setUpdatedBy("1");
				asset = assetDao.saveOrUpdate(asset);

				Inventory inventory = asset.getInventoryId();
				if (asset.getStatusAssetId().getCodeStatusAsset().equalsIgnoreCase(StatusAssetEnum.DEPLOY.getCode())) {
					int stockInventory = inventory.getLatestStock();
					int latestStock = stockInventory + 1;

					inventory.setUpdatedBy("1");
					inventory.setLatestStock(latestStock);

					inventory = inventoryService.saveOrUpdate(inventory);
				}

				TrackAsset trackAsset = new TrackAsset();
				trackAsset.setCodeAsset(asset.getCode());
				trackAsset.setNameActivity(data.getStatusTrCheckinId().getNameStatusTr());
				trackAsset.setDateActivity(LocalDate.now());
				trackAsset.setUserId("33");
				trackAsset.setTransactionCode(data.getTransactionId().getCodeTransaction());
				trackAsset.setCreatedBy(data.getCreatedBy());
				trackAsset.setIsActive(data.getIsActive());
				trackAssetService.saveOrUpdate(trackAsset);

				commit();
			} else {
				data.setStatusEmail(false);
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

	@Override
	@Async
	@Scheduled(fixedDelay = 60000)
	public List<TransactionDetail> expDurationAssign() throws Exception {
		List<TransactionDetail> listResult = transactionDetailDao.expDurationAssign();

		if (listResult != null) {
			for (int i = 0; i < listResult.size(); i++) {
				if (listResult.get(i).getTransactionId().getEmployeeId() != null) {
					String emailEmployee = listResult.get(i).getTransactionId().getEmployeeId().getEmailEmployee();
					String emailAssign = listResult.get(i).getTransactionId().getUserId().getEmail();

					EmailHelper email = new EmailHelper();
					email.setEmployeeName(listResult.get(i).getTransactionId().getEmployeeId().getNameEmployee());
					email.setValueName(listResult.get(i).getAssetId().getNames());
					email.setExpiredDate(listResult.get(i).getDurationDate());
					
					emailHandler.sendSimpleMessage(emailAssign, "Expired Asset Reminder", "Close To Expired", email);
					emailHandler.sendSimpleMessage(emailEmployee, "Expired Asset Reminder", "Close To Expired", email);
					TransactionDetail transactionDetail = listResult.get(i);
					transactionDetail.setUpdatedBy("1");
					transactionDetail.setStatusEmail(true);
					
					begin();
					transactionDetailDao.saveOrUpdate(transactionDetail);
					commit();
				}
			}
		}

		return null;
	}

}
