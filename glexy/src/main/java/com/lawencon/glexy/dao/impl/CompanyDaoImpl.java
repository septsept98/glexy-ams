package com.lawencon.glexy.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.BaseDaoImpl;
import com.lawencon.glexy.dao.CompanyDao;
import com.lawencon.glexy.model.Company;

@Repository
public class CompanyDaoImpl extends BaseDaoImpl<Company> implements CompanyDao {

	@Override
	public Company saveOrUpdate(Company data) throws Exception {
		return save(data);
	}

	@Override
	public boolean removeById(String id) throws Exception {
		return deleteById(id);
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
