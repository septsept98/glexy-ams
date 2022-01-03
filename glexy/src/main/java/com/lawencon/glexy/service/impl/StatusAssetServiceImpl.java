package com.lawencon.glexy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.glexy.dao.AssetDao;
import com.lawencon.glexy.dao.StatusAssetDao;
import com.lawencon.glexy.dao.StatusTransactionDao;
import com.lawencon.glexy.dao.TransactionDetailDao;
import com.lawencon.glexy.exception.ValidationGlexyException;
import com.lawencon.glexy.model.Asset;
import com.lawencon.glexy.model.StatusAsset;
import com.lawencon.glexy.model.StatusTransaction;
import com.lawencon.glexy.model.TransactionDetail;
import com.lawencon.glexy.service.StatusAssetService;

@Service
public class StatusAssetServiceImpl extends BaseGlexyServiceImpl implements StatusAssetService {

	@Autowired
	private StatusAssetDao statusAssetDao;

	@Autowired
	private AssetDao assetDao;

	@Autowired
	private TransactionDetailDao transactionDetailDao;

	@Autowired
	private StatusTransactionDao statusTransactionDao;

	@Override
	public StatusAsset saveOrUpdate(StatusAsset data) throws Exception {
		try {
			if (data.getId() != null) {
				validationUpdate(data);
				StatusAsset statusAsset = findById(data.getId());
				data.setCodeStatusAsset(statusAsset.getCodeStatusAsset());
				data.setCreatedAt(statusAsset.getCreatedAt());
				data.setCreatedBy(statusAsset.getCreatedBy());
				data.setUpdatedBy(getIdAuth());
				data.setVersion(statusAsset.getVersion());
			} else {
				validationSave(data);
				data.setCreatedBy(getIdAuth());
				data.setCodeStatusAsset(generateCodeSA());
			}

			begin();
			data = statusAssetDao.saveOrUpdate(data);
			commit();
			return data;
		} catch (Exception e) {
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
			result = statusAssetDao.removeById(id);
			commit();
			return result;
		} catch (Exception e) {
			rollback();
			throw new Exception(e);
		}
	}

	@Override
	public StatusAsset findById(String id) throws Exception {
		return statusAssetDao.findById(id);
	}

	@Override
	public List<StatusAsset> findAll() throws Exception {
		return statusAssetDao.findAll();
	}

	@Override
	public StatusAsset findByCode(String code) throws Exception {
		return statusAssetDao.findByCode(code);
	}

	@Override
	public String generateCodeSA() throws Exception {
		String code = "SA";
		List<StatusAsset> listStatusAsset = findAll();
		int index = 1;
		if (listStatusAsset != null) {
			index = listStatusAsset.size();
		}
		String codeSA = code + index;
		for (int i = 0; i < listStatusAsset.size(); i++) {
			if (listStatusAsset.get(i).getCodeStatusAsset().equals(codeSA)) {
				index++;
			}
			codeSA = code + index;
		}

		return codeSA;
	}

	@Override
	public void validationFk(String id) throws Exception {
		List<Asset> dataAsset = assetDao.findByStatusAssetId(id);
		List<TransactionDetail> dataTranscation = transactionDetailDao.findByStatusAssetId(id);
		List<StatusTransaction> dataStatusTransaction = statusTransactionDao.findByStatusAssetId(id);
		if (dataAsset.size() != 0|| dataTranscation.size() != 0 || dataStatusTransaction.size() != 0) {
			throw new ValidationGlexyException("Status Asset in Use");
		}

	}

	@Override
	public void validationSave(StatusAsset data) throws Exception {
		if (data != null) {
			List<StatusAsset> listStatusAssets = statusAssetDao.findAll();
			for (int i = 0; i < listStatusAssets.size(); i++) {
				if (data.getNameStatusAsset().equalsIgnoreCase(listStatusAssets.get(i).getNameStatusAsset())) {
					throw new ValidationGlexyException("Name Status Already Exist");
				}
			}
			if (data.getNameStatusAsset().isBlank() || data.getIsActive() == null) {
				throw new ValidationGlexyException("Data not Complete");
			}
		} else {
			throw new ValidationGlexyException("Data Empty");
		}
	}

	@Override
	public void validationUpdate(StatusAsset data) throws Exception {
		if (data != null) {
			List<StatusAsset> listStatusAssets = statusAssetDao.findAll();
			for (int i = 0; i < listStatusAssets.size(); i++) {
				if (data.getNameStatusAsset().equalsIgnoreCase(listStatusAssets.get(i).getNameStatusAsset())
						&& !data.getId().equals(listStatusAssets.get(i).getId())) {
					throw new ValidationGlexyException("Name Status Already Exist");
				}
			}
			if (data.getId() != null) {
				StatusAsset statusAsset = findById(data.getId());
				if (statusAsset == null) {
					throw new ValidationGlexyException("Data not Found");
				}
			} else {
				throw new ValidationGlexyException("Data not Found");
			}
			if (data.getCodeStatusAsset().isBlank() || data.getNameStatusAsset().isBlank() || data.getVersion() == null) {
				throw new ValidationGlexyException("Data not Complete");
			}
		} else {
			throw new ValidationGlexyException("Data Empty");
		}

	}

	@Override
	public List<StatusAsset> findByName(String name) throws Exception {
		return statusAssetDao.findByName(name);
	}

}
