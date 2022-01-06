package com.lawencon.glexy.service.impl;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.glexy.dao.AssetDao;
import com.lawencon.glexy.dao.InventoryDao;
import com.lawencon.glexy.exception.ValidationGlexyException;
import com.lawencon.glexy.model.Asset;
import com.lawencon.glexy.model.Inventory;
import com.lawencon.glexy.service.InventoryService;

@Service

public class InventoryServiceImpl extends BaseGlexyServiceImpl implements InventoryService {

	@Autowired
	private InventoryDao inventoryDao;

	@Autowired
	private AssetDao assetDao;

	@Override
	public Inventory saveOrUpdate(Inventory data) throws Exception {
		try {
			if (data.getId() != null) {
				validationUpdate(data);
				Inventory inventory = findById(data.getId());
				data.setNameAsset(inventory.getNameAsset());
				data.setCode(inventory.getCode());
				data.setCreatedAt(inventory.getCreatedAt());
				data.setCreatedBy(inventory.getCreatedBy());
				data.setUpdatedBy(getIdAuth());
				data.setVersion(inventory.getVersion());
			} else {

				data.setCreatedBy(getIdAuth());
				data.setIsActive(true);
				validationSave(data);

			}
			data = inventoryDao.saveOrUpdate(data);
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
		}
		return data;
	}

	@Override
	public Inventory findById(String id) throws Exception {
		Inventory result = new Inventory();
		try {
			result = inventoryDao.findById(id);
		} catch (NoResultException e) {
			e.printStackTrace();
			throw new NoResultException("Inventory not found");
		}
		return result;
	}

	@Override
	public List<Inventory> findAll() throws Exception {
		return inventoryDao.findAll();
	}

	@Override
	public List<Inventory> searchByNameCode(String search) throws Exception {
		return inventoryDao.searchByNameCode(search);
	}

	@Override
	public Inventory findByCode(String code) throws Exception {
		return inventoryDao.findByCode(code);
	}

	@Override
	public boolean removeById(String id) throws Exception {
		boolean result = false;
		try {
			validationFk(id);
			begin();
			result = inventoryDao.removeById(id);
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
		}
		return result;
	}

	@Override
	public String generateCode(int index) throws Exception {
		return "inven" + "" + (index + 1);
	}

	@Override
	public void validationFk(String id) throws Exception {
		List<Asset> dataAsset = assetDao.findByInventoryId(id);
		if (dataAsset.size() != 0) {

			throw new ValidationGlexyException("Inventory Type in Use");
		}

	}

	@Override
	public void validationSave(Inventory data) throws Exception {

		if (data != null) {
			if (data.getCode().isBlank()  || data.getStock() == null || data.getNameAsset().isBlank() ) {
				throw new ValidationGlexyException("Data not Complete");
			}
		} else {
			throw new ValidationGlexyException("Data Empty");
		}
	}

	@Override
	public void validationUpdate(Inventory data) throws Exception {
		if (data != null) {
			if (data.getId() != null) {
				Inventory inventory = findById(data.getId());
				if (inventory == null) {
					throw new ValidationGlexyException("Data not Found");
				}
			} else {
				throw new ValidationGlexyException("Data not Found");
			}
			if (data.getCode() == null || data.getLatestStock() == null || data.getNameAsset() == null) {
				throw new ValidationGlexyException("Data not Complete");
			}
		} else {
			throw new ValidationGlexyException("Data Empty");
		}
	}

	@Override
	public List<Inventory> searchByCodeNameComp(String search) throws Exception {
		return inventoryDao.searchByCodeNameComp(search);
	}

	@Override
	public List<Inventory> searchByCodeNameLicn(String search) throws Exception {
		return inventoryDao.searchByCodeNameLicn(search);
	}

}
