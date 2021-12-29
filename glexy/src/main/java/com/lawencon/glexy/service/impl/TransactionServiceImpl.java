package com.lawencon.glexy.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.glexy.constant.StatusAssetEnum;
import com.lawencon.glexy.dao.AssetDao;
import com.lawencon.glexy.dao.TransactionDao;
import com.lawencon.glexy.dto.transaction.InsertReqDataAssetTransactionDto;
import com.lawencon.glexy.dto.transaction.InsertReqTransactionDto;
import com.lawencon.glexy.exception.ValidationGlexyException;
import com.lawencon.glexy.model.Asset;
import com.lawencon.glexy.model.Inventory;
import com.lawencon.glexy.model.StatusAsset;
import com.lawencon.glexy.model.TrackAsset;
import com.lawencon.glexy.model.TransactionDetail;
import com.lawencon.glexy.model.Transactions;
import com.lawencon.glexy.model.Users;
import com.lawencon.glexy.service.InventoryService;
import com.lawencon.glexy.service.StatusAssetService;
import com.lawencon.glexy.service.TrackAssetService;
import com.lawencon.glexy.service.TransactionDetailService;
import com.lawencon.glexy.service.TransactionService;

@Service
public class TransactionServiceImpl extends BaseServiceImpl implements TransactionService {

	private TransactionDao transactionDao;
	private TransactionDetailService transactionDetailService;
	private TrackAssetService trackAssetService;
	private AssetDao assetDao;
	private StatusAssetService statusAssetService;
	private InventoryService inventoryService;

	public TransactionServiceImpl(@Autowired TransactionDao transactionDao,
			TransactionDetailService transactionDetailService, TrackAssetService trackAssetService, AssetDao assetDao,
			StatusAssetService statusAssetService, InventoryService inventoryService) {
		this.transactionDao = transactionDao;
		this.transactionDetailService = transactionDetailService;
		this.trackAssetService = trackAssetService;
		this.assetDao = assetDao;
		this.statusAssetService = statusAssetService;
		this.inventoryService = inventoryService;
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
	public List<Asset> findAssetDetail(InsertReqDataAssetTransactionDto data) throws Exception {
		int qtyTr = data.getQty();
		String nameAsset = data.getName();
		boolean sameAsset = false;
		int indexInven = 0;
		List<Inventory> listInventory = inventoryService.findAll();

		for (int i = 0; i < listInventory.size(); i++) {
			if (nameAsset.equalsIgnoreCase(listInventory.get(i).getNameAsset())) {
				sameAsset = true;
				indexInven = i;
			}
		}

		List<Asset> listAssetTr = new ArrayList<>();
		Inventory inventory = new Inventory();
		if (sameAsset) {
			inventory = listInventory.get(indexInven);
			List<Asset> listAssetInvent = assetDao.findByInvent(inventory.getId());

			if (listAssetInvent.size() >= qtyTr) {
				for (int i = 0; i < qtyTr; i++) {
					listAssetTr.add(listAssetInvent.get(i));
				}
			}
		}

		return listAssetTr;
	}

	@Override
	public InsertReqTransactionDto saveOrUpdate(InsertReqTransactionDto data) throws Exception {

		if (data.getDataTransaction().getEmployeeId() == null && data.getDataTransaction().getLocationId() == null
				&& data.getDataTransaction().getAssetGeneralId() == null) {
			throw new ValidationGlexyException("Data not Complete");
		} else if (data.getDataTransaction().getEmployeeId() != null
				&& data.getDataTransaction().getLocationId() != null
				&& data.getDataTransaction().getAssetGeneralId() == null) {
			throw new ValidationGlexyException("Must one");
		} else if (data.getDataTransaction().getEmployeeId() == null
				&& data.getDataTransaction().getLocationId() != null
				&& data.getDataTransaction().getAssetGeneralId() != null) {
			throw new ValidationGlexyException("Must one");
		} else if (data.getDataTransaction().getEmployeeId() != null
				&& data.getDataTransaction().getLocationId() == null
				&& data.getDataTransaction().getAssetGeneralId() != null) {
			throw new ValidationGlexyException("Must one");
		} else if (data.getDataTransaction().getEmployeeId() != null
				&& data.getDataTransaction().getLocationId() != null
				&& data.getDataTransaction().getAssetGeneralId() != null) {
			throw new ValidationGlexyException("Must one");
		} else {
			InsertReqTransactionDto result = new InsertReqTransactionDto();

			Transactions dataTransaction = data.getDataTransaction();
			dataTransaction.setCodeTransaction(generateCode());
			dataTransaction.setCreatedBy("3");
			Users users = new Users();
			users.setId("1");
			dataTransaction.setUserId(users);
			dataTransaction.setCheckOutDate(LocalDate.now());
			validationSave(dataTransaction);
			begin();
			dataTransaction = transactionDao.saveOrUpdate(dataTransaction);

			List<TransactionDetail> listDataDetailTransaction = data.getDataDetailTransaction();

			for (int i = 0; i < listDataDetailTransaction.size(); i++) {
				TransactionDetail detailTr = listDataDetailTransaction.get(i);
				detailTr.setTransactionId(dataTransaction);
				Asset asset = assetDao.findById(detailTr.getAssetId().getId());
				detailTr.setAssetId(asset);
				detailTr.setCreatedBy(dataTransaction.getCreatedBy());
				detailTr.setIsActive(dataTransaction.getIsActive());

				StatusAsset statusCheckOut = statusAssetService.findByCode(StatusAssetEnum.DEPLOY.getCode());
				detailTr.setStatusAssetCheckoutId(statusCheckOut);

				transactionDetailService.saveOrUpdate(detailTr);

				StatusAsset statusAsset = statusAssetService.findByCode(StatusAssetEnum.ASSIGN.getCode());
				System.out.println(asset.getExpiredDate() + " - " + asset.getNames());
				if (asset.getId() != null) {
					asset.setStatusAssetId(statusAsset);
					asset.setUpdatedBy("22");

					assetDao.saveOrUpdate(asset);
				}

				boolean sameInvent = false;
				int indexInvent = 0;
				List<Inventory> listInventory = inventoryService.findAll();
				for (int j = 0; j < listInventory.size(); j++) {
					if (listInventory.get(j).getNameAsset().equalsIgnoreCase(asset.getNames())) {
						sameInvent = true;
						indexInvent = j;
					}
				}

				if (sameInvent) {
					int stock = listInventory.get(indexInvent).getLatestStock() - 1;
					listInventory.get(indexInvent).setLatestStock(stock);
					inventoryService.saveOrUpdate(listInventory.get(indexInvent));
				}

				TrackAsset trackAsset = new TrackAsset();
				trackAsset.setCodeAsset(asset.getCode());
				trackAsset.setNameActivity(statusAsset.getNameStatusAsset());
				trackAsset.setDateActivity(LocalDate.now());
				trackAsset.setUserId("33");
				trackAsset.setTransactionCode(dataTransaction.getCodeTransaction());
				trackAsset.setCreatedBy(dataTransaction.getCreatedBy());
				trackAsset.setIsActive(dataTransaction.getIsActive());

				trackAssetService.saveOrUpdate(trackAsset);

			}
			commit();

			return result;
		}
	}

	@Override
	public void validationSave(Transactions data) throws Exception {
		if (data != null) {
			if (data.getDescription().isBlank()) {
				throw new ValidationGlexyException("Data not Complete");
			}
		}
	}

	@Override
	public String generateCode() throws Exception {
		String number = "123456789";
		StringBuilder sb = new StringBuilder();
		sb.append("TRX");
		Random random = new Random();
		for (int i = 0; i < 7; i++) {
			int indNumber = random.nextInt(number.length());
			sb.append(number.charAt(indNumber));
		}
		return sb.toString();
	}

}
