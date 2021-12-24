package com.lawencon.glexy.dao.impl;

import java.util.ArrayList;
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

	@Override
	public List<Company> findByNameCode(String search) throws Exception {
		List<Company> listResult = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("Select id ");
		sql.append("FROM companies ");
		sql.append("WHERE code LIKE '%" + search + "%' OR names LIKE '%" + search + "%' ");

		List<?> result = createNativeQuery(sql.toString()).getResultList();

		result.forEach(rs -> {
			Company company = new Company();
			company.setId(rs.toString());
			company = getById(company.getId());

			listResult.add(company);
		});

		return listResult;
	}

}
