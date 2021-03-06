package com.lawencon.glexy.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.lawencon.glexy.constant.StatusAssetEnum;
import com.lawencon.glexy.dao.AssetDao;
import com.lawencon.glexy.dao.TransactionDetailDao;
import com.lawencon.glexy.dto.ResDto;
import com.lawencon.glexy.email.EmailHandler;
import com.lawencon.glexy.exception.ValidationGlexyException;
import com.lawencon.glexy.helper.EmailHelper;
import com.lawencon.glexy.helper.ReportDataTransactionOutDate;
import com.lawencon.glexy.model.Asset;
import com.lawencon.glexy.model.Company;
import com.lawencon.glexy.model.Inventory;
import com.lawencon.glexy.model.TrackAsset;
import com.lawencon.glexy.model.TransactionDetail;
import com.lawencon.glexy.model.Users;
import com.lawencon.glexy.service.InventoryService;
import com.lawencon.glexy.service.TrackAssetService;
import com.lawencon.glexy.service.TransactionDetailService;
import com.lawencon.glexy.service.UsersService;
import com.lawencon.util.JasperUtil;

@Service
public class TransactionDetailServiceImpl extends BaseGlexyServiceImpl implements TransactionDetailService {

	@Autowired
	private TransactionDetailDao transactionDetailDao;

	@Autowired
	private AssetDao assetDao;

	@Autowired
	private TrackAssetService trackAssetService;

	@Autowired
	private UsersService usersService;

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
				data.setUpdatedBy(getIdAuth());
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
				asset.setUpdatedBy(getIdAuth());
				asset = assetDao.saveOrUpdate(asset);

				Inventory inventory = asset.getInventoryId();
				if (asset.getStatusAssetId().getCodeStatusAsset().equalsIgnoreCase(StatusAssetEnum.DEPLOY.getCode())) {
					int stockInventory = inventory.getLatestStock();
					int latestStock = stockInventory + 1;

					inventory.setUpdatedBy(getIdAuth());
					inventory.setLatestStock(latestStock);

					inventory = inventoryService.saveOrUpdate(inventory);
				}

				TrackAsset trackAsset = new TrackAsset();
				trackAsset.setCodeAsset(asset.getCode());
				trackAsset.setNameActivity(data.getStatusTrCheckinId().getNameStatusTr());
				trackAsset.setDateActivity(LocalDate.now());
				trackAsset.setUserId(getIdAuth());
				trackAsset.setTransactionCode(data.getTransactionId().getCodeTransaction());
				trackAsset.setCreatedBy(data.getCreatedBy());
				trackAsset.setIsActive(data.getIsActive());
				trackAssetService.saveOrUpdate(trackAsset);

				commit();
			} else {
				validationSave(data);
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

					emailHandler.sendExpiredMessage(emailAssign, "Expired Asset Reminder", "Close To Expired", email);  
					emailHandler.sendExpiredMessage(emailEmployee, "Expired Asset Reminder", "Close To Expired", email);
          
					TransactionDetail transactionDetail = listResult.get(i);
					transactionDetail.setUpdatedBy("2");
					transactionDetail.setStatusEmail(true);

					begin();
					transactionDetailDao.saveOrUpdate(transactionDetail);
					commit();
				}
			}
		}

		return null;
	}

	@Override
	public List<ReportDataTransactionOutDate> reportAllDataOutDate() throws Exception {
		List<ReportDataTransactionOutDate> listResult = new ArrayList<>();
		List<TransactionDetail> listTrxDetail = findAllOutDate();

		for (int i = 0; i < listTrxDetail.size(); i++) {
			TransactionDetail detail = listTrxDetail.get(i);
			ReportDataTransactionOutDate dataTrxOutDate = new ReportDataTransactionOutDate();
			if (detail.getTransactionId().getEmployeeId() != null) {
				dataTrxOutDate.setCodeTrx(detail.getTransactionId().getCodeTransaction());
				dataTrxOutDate.setEmployeeName(detail.getTransactionId().getEmployeeId().getNameEmployee());
				dataTrxOutDate.setNip(detail.getTransactionId().getEmployeeId().getNip());
				dataTrxOutDate.setEmail(detail.getTransactionId().getEmployeeId().getEmailEmployee());
				dataTrxOutDate.setCodeAsset(detail.getAssetId().getCode());
				dataTrxOutDate.setNameAsset(detail.getAssetId().getNames());
				dataTrxOutDate.setImage(detail.getAssetId().getAssetImg().getFile());
				dataTrxOutDate.setDueDate(detail.getDurationDate());
				dataTrxOutDate.setCheckinDate(detail.getDateCheckin());

				listResult.add(dataTrxOutDate);
			}
		}

		return listResult;
	}

	@Override
	public byte[] pdfTransactionOutDate() throws Exception {
		Users users = usersService.findByIdAuth();
		Company company = users.getEmployeeId().getCompanyId();
		HashMap<String, Object> map = new HashMap<>();
		map.put("company", company.getNames());
		map.put("address", company.getAddress());
		map.put("website", company.getWebsite());
		map.put("telp", company.getPhoneNumber());
		map.put("fax", company.getFax());
		map.put("description", company.getDescription());
		map.put("logo", company.getId());
		map.put("title", "TRANSACTION OUT of DATE");
		
		byte[] data = JasperUtil.responseToByteArray(reportAllDataOutDate(), "trx-outdate", map);

		return data;
	}

	@Override
	public List<TransactionDetail> findAllOutDate() throws Exception {
		return transactionDetailDao.findAllOutDate();
	}

	@Override
	public ResDto sendEmailTrxExpiredReport() throws Exception {
		byte[] data = pdfTransactionOutDate();
		
		EmailHelper emailHelper = new EmailHelper();
		emailHelper.setAttach(data);
		emailHelper.setFileName("trx-outdate.pdf");
		
		Users users = usersService.findByIdAuth();
		
		emailHandler.sendReport(users.getEmail(), "Transaction Expired Report", "Expired Transaction", emailHelper);
		
		ResDto resDto = new ResDto();
		resDto.setMsg("Send to Email");
		
		return resDto;
	}
  
	public void validationSave(TransactionDetail data) throws Exception {
		if(data.getAssetId().getId().isBlank()) {
			throw new ValidationGlexyException("Data not Complete");
		}
		
	}

	@Override
	public List<TransactionDetail> findByTrNotCheckIn(String id) throws Exception {
		return transactionDetailDao.findByTrNotCheckIn(id);
	}

	@Override
	public List<TransactionDetail> findAllNotCheckIn() throws Exception {
		return transactionDetailDao.findAllNotCheckIn();
	}

	@Override
	public List<TransactionDetail> findAllCheckIn() throws Exception {
		return transactionDetailDao.findAllCheckIn();
	}

	@Override
	public List<TransactionDetail> findByTrCheckIn(String id) throws Exception {
		return transactionDetailDao.findByTrCheckIn(id);
	}

}
