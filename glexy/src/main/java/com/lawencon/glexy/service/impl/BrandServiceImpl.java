package com.lawencon.glexy.service.impl;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.glexy.dao.AssetDao;
import com.lawencon.glexy.dao.BrandDao;
import com.lawencon.glexy.exception.ValidationGlexyException;
import com.lawencon.glexy.model.Asset;
import com.lawencon.glexy.model.Brand;
import com.lawencon.glexy.service.BrandService;

@Service
public class BrandServiceImpl extends BaseGlexyServiceImpl implements BrandService {

	@Autowired
	private BrandDao brandDao;

	@Autowired
	private AssetDao assetDao;

	@Override
	public Brand saveOrUpdate(Brand data) throws Exception {
		try {
			if (data.getId() != null) {
				validationUpdate(data);
				Brand brand = findById(data.getId());
				data.setCode(brand.getCode());
				data.setCreatedAt(brand.getCreatedAt());
				data.setCreatedBy(brand.getCreatedBy());
				data.setUpdatedBy(getIdAuth());
				data.setVersion(brand.getVersion());
			} else {
				data.setCreatedBy("1");
				validationSave(data);
			}

			begin();
			data = brandDao.saveOrUpdate(data);
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
		}
		return data;
	}

	@Override
	public Brand findById(String id) throws Exception {
		Brand result = new Brand();
		try {
			result = brandDao.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new NoResultException("Brand not found");
		}
		return result;
	}

	@Override
	public List<Brand> findAll() throws Exception {
		return brandDao.findAll();
	}

	@Override
	public boolean removeById(String id) throws Exception {
		boolean result = false;
		try {
			validationFk(id);
			begin();
			result = brandDao.removeById(id);
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
		}
		return result;
	}

	@Override
	public List<Brand> searchByNameCode(String search) throws Exception {
		return brandDao.searchByNameCode(search);
	}

	@Override
	public Brand findByCode(String code) throws Exception {
		return brandDao.findByCode(code);
	}

	@Override
	public void validationFk(String id) throws Exception {

		List<Asset> dataAsset = assetDao.findByBrandId(id);

		if (dataAsset.size() != 0) {

			throw new ValidationGlexyException("Brand Type in Use");
		}

	}

	@Override
	public void validationSave(Brand data) throws Exception {
		if (data != null) {
			if (data.getCode().isBlank()  || data.getNames().isBlank()  || data.getIsActive() == null) {

				throw new ValidationGlexyException("Data not Complete");
			}
		} else {
			throw new ValidationGlexyException("Data Empty");
		}
	}

	@Override
	public void validationUpdate(Brand data) throws Exception {
		if (data != null) {
			if (data.getId() != null) {
				Brand brand = findById(data.getId());
				if (brand == null) {
					throw new ValidationGlexyException("Data not Found");
				}
			} else {
				throw new ValidationGlexyException("Data not Found");
			}
			if (data.getCode().isBlank()  || data.getNames().isBlank()  || data.getIsActive() == null) {

				throw new ValidationGlexyException("Data not Complete");
			}
		} else {
			throw new ValidationGlexyException("Data Empty");
		}
	}

}
