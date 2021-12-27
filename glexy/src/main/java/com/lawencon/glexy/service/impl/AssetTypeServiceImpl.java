package com.lawencon.glexy.service.impl;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.glexy.dao.AssetDao;
import com.lawencon.glexy.dao.AssetTypeDao;
import com.lawencon.glexy.exception.ValidationGlexyException;
import com.lawencon.glexy.model.Asset;
import com.lawencon.glexy.model.AssetType;
import com.lawencon.glexy.service.AssetTypeService;

@Service
public class AssetTypeServiceImpl extends BaseGlexyServiceImpl implements AssetTypeService {

	@Autowired
	private AssetTypeDao assetTypeDao;

	@Autowired
	private AssetDao assetDao;

	@Override
	public AssetType saveOrUpdate(AssetType data) throws Exception {
		try {
			if (data.getId() != null) {
				validationUpdate(data);
				AssetType assetType = findById(data.getId());
				data.setCode(assetType.getCode());
				data.setCreatedAt(assetType.getCreatedAt());
				data.setCreatedBy(assetType.getCreatedBy());
				data.setUpdatedBy(getIdAuth());
				data.setVersion(assetType.getVersion());
			} else {

				data.setCreatedBy(getIdAuth());

				validationSave(data);

			}

			begin();
			data = assetTypeDao.saveOrUpdate(data);
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
		}
		return data;
	}

	@Override
	public AssetType findById(String id) throws Exception {
		AssetType result = new AssetType();
		try {
			result = assetTypeDao.findById(id);
		} catch (NoResultException e) {
			e.printStackTrace();
			throw new NoResultException("Asset Type not found");
		}
		return result;
	}

	@Override
	public List<AssetType> findAll() throws Exception {
		return assetTypeDao.findAll();
	}

	@Override
	public List<AssetType> searchByNameCode(String search) throws Exception {
		return assetTypeDao.searchByNameCode(search);
	}

	@Override
	public boolean removeById(String id) throws Exception {
		boolean result = false;
		try {
			validationFk(id);
			begin();
			result = assetTypeDao.removeById(id);
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
		}
		return result;
	}

	@Override
	public AssetType findByCode(String code) throws Exception {
		return assetTypeDao.findByCode(code);
	}

	@Override
	public void validationFk(String id) throws Exception {
		List<Asset> dataEmployee = assetDao.findByAssetTypeId(id);
		if (dataEmployee != null) {

			throw new ValidationGlexyException("Asset Type in Use");
		}

	}

	@Override
	public void validationSave(AssetType data) throws Exception {
		if (data.getCode() == null || data.getIsActive() == null || data.getNames() == null) {

			throw new ValidationGlexyException("Data not Complete");

		}

	}

	@Override
	public void validationUpdate(AssetType data) throws Exception {
		if (data.getId() != null) {
			AssetType assetType = findById(data.getId());
			if (assetType == null) {
				throw new ValidationGlexyException("Data not Found");
			}
		} else {
			throw new ValidationGlexyException("Data not Found");
		}
		if (data.getCode() == null || data.getIsActive() == null || data.getNames() == null) {

			throw new ValidationGlexyException("Data not Complete");

		}

	}

}
