package com.lawencon.glexy.service.impl;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.glexy.dao.BrandDao;
import com.lawencon.glexy.model.Brand;
import com.lawencon.glexy.service.BrandService;

@Service
public class BrandServiceImpl extends BaseGlexyServiceImpl implements BrandService {

	@Autowired
	private BrandDao brandDao;
	
	@Override
	public Brand saveOrUpdate(Brand data) throws Exception {
		try {
			if(data.getId() != null) {
				Brand brand = findById(data.getId());
				data.setCode(brand.getCode());
				data.setCreatedAt(brand.getCreatedAt());
				data.setCreatedBy(brand.getCreatedBy());
				data.setUpdatedBy(getIdAuth());
				data.setVersion(brand.getVersion());
			} else {
				data.setCreatedBy(getIdAuth());
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
	public Brand findByCode(String code) throws Exception {
		return brandDao.findByCode(code);
	}
	
	
	
	
	

}
