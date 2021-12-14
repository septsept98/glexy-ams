package com.lawencon.glexy.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.BaseDaoImpl;
import com.lawencon.glexy.model.Brand;

@Repository
public class BrandDaoImpl extends BaseDaoImpl<Brand> implements BrandDao{

	@Override
	public Brand saveOrUpdate(Brand data) throws Exception {
		return save(data);
	}

	@Override
	public boolean removeById(String id) throws Exception {
		return deleteById(id);
	}

	@Override
	public Brand findById(String id) throws Exception {
		return getById(id);
	}

	@Override
	public List<Brand> findAll() throws Exception {
		return getAll();
	}
	

}
