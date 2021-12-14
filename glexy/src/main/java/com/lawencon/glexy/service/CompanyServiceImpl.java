package com.lawencon.glexy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.glexy.dao.CompanyDao;
import com.lawencon.glexy.model.Company;

public class CompanyServiceImpl extends BaseServiceImpl implements CompanyService {

	@Autowired
	private CompanyDao companyDao;
	
	@Override
	public Company saveOrUpdate(Company data) throws Exception {
		try {
			if(data.getId() != null) {
				Company company = findById(data.getId());
				data.setCreatedAt(company.getCreatedAt());
				data.setCreatedBy(company.getCreatedBy());
				data.setVersion(company.getVersion());
			}
			
			begin();
			data = companyDao.saveOrUpdate(data);
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
		}
		return data;
	}

	@Override
	public Company findById(String id) throws Exception {
		return companyDao.findById(id);
	}

	@Override
	public List<Company> findAll() throws Exception {
		return companyDao.findAll();
	}

	@Override
	public boolean removeById(String id) throws Exception {
		boolean result = false;
		try {
			begin();
			result = companyDao.removeById(id);
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
		}
		return result;
	}
	
	

	
}
