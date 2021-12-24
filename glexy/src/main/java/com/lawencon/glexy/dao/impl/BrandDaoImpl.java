package com.lawencon.glexy.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.BaseDaoImpl;
import com.lawencon.glexy.dao.BrandDao;
import com.lawencon.glexy.model.Brand;

@Repository
public class BrandDaoImpl extends BaseDaoImpl<Brand> implements BrandDao {

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

	@Override
	public List<Brand> findByNameCode(String search) throws Exception {
		List<Brand> listResult = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("Select id ");
		sql.append("FROM brands ");
		sql.append("WHERE code LIKE '%" + search + "%' OR names LIKE '%" + search + "%' ");

		List<?> result = createNativeQuery(sql.toString()).getResultList();

		result.forEach(rs -> {
			Brand brand = new Brand();
			brand.setId(rs.toString());
			brand = getById(brand.getId());

			listResult.add(brand);
		});

		return listResult;
	}

}
