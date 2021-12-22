package com.lawencon.glexy.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import org.springframework.stereotype.Repository;

import com.lawencon.base.BaseDaoImpl;
import com.lawencon.glexy.dao.CompanyDao;
import com.lawencon.glexy.model.Asset;
import com.lawencon.glexy.model.AssetType;
import com.lawencon.glexy.model.Company;
import com.lawencon.glexy.model.Location;

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

	@Override
	public Company findByCode(String code) throws Exception {
		Company company = new Company();
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("Select id ");
			sql.append("FROM companies ");
			sql.append("WHERE code=:code");
			
			Object result = createNativeQuery(sql.toString())
					.setParameter("code", code)
					.getSingleResult();	
			if(result != null) {
				company.setId(result.toString());
				company = getById(company.getId());
			}

		}  catch (NoResultException e) {
			e.printStackTrace();
		} catch (NonUniqueResultException e) {
			e.printStackTrace();
		}
		return company;
	}

	
	
	
	

}
