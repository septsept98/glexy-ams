package com.lawencon.glexy.dao;

import java.util.List;

import com.lawencon.base.BaseDaoImpl;
import com.lawencon.glexy.model.Company;

public class CompanyDaoImpl extends BaseDaoImpl<Company> implements CompanyDao {

	@Override
	public void insert(Company data) throws Exception {
		save(data);		
	}

	@Override
	public void update(Company data) throws Exception {
		save(data);		
	}

	@Override
	public Company findById(String id) throws Exception {
		return getById(id);
	}

	@Override
	public List<Company> findAll() throws Exception {
		return getAll();
	}
	

}
