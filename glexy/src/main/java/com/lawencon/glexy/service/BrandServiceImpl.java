package com.lawencon.glexy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.glexy.dao.BrandDao;
import com.lawencon.glexy.model.Brand;

@Service
public class BrandServiceImpl extends BaseServiceImpl implements BrandService {

	@Autowired
	private BrandDao brandDao;
	
	@Override
	public Brand saveOrUpdate(Brand data) throws Exception {
		try {
			if(data.getId() != null) {
				Brand brand = findById(data.getId());
				data.setCreatedAt(brand.getCreatedAt());
				data.setCreatedBy(brand.getCreatedBy());
				data.setVersion(brand.getVersion());
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
		return brandDao.findById(id);
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
	
	
	

}
