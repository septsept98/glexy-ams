package com.lawencon.glexy.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

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
	public List<Brand> searchByNameCode(String search) throws Exception {
		List<Brand> listResult = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("Select id ");
		sql.append("FROM brands ");
		sql.append("WHERE lower(code) LIKE lower('%" + search + "%') OR lower(names) LIKE lower('%" + search + "%') ");

		List<?> result = createNativeQuery(sql.toString()).getResultList();

		result.forEach(rs -> {
			Brand brand = new Brand();
			brand.setId(rs.toString());
			brand = getById(brand.getId());

			listResult.add(brand);
		});

		return listResult;
	}

	@Override
	public Brand findByCode(String code) throws Exception {
		Brand brand = new Brand();
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("Select id ");
			sql.append("FROM brands ");
			sql.append("WHERE code=:code");

			Object result = createNativeQuery(sql.toString()).setParameter("code", code).getSingleResult();
			if (result != null) {
				brand.setId(result.toString());
				brand = getById(brand.getId());
			}

		} catch (NoResultException e) {
			e.printStackTrace();
		} catch (NonUniqueResultException e) {
			e.printStackTrace();
		}
		return brand;
	}

	@Override
	public List<Brand> findAllFilter(String search) throws Exception {
		List<Brand> listResult = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("Select b.id ");
		sql.append("FROM brands b ");
		sql.append("INNER JOIN assets a ON a.brand_id = b.id ");
		sql.append("INNER JOIN inventories i ON a.inventory_id = i.id ");
		sql.append("WHERE i.id LIKE '%" + search + "%'");
		sql.append("GROUP BY b.id ");

		List<?> result = createNativeQuery(sql.toString()).getResultList();
		System.out.println(result);

		result.forEach(rs -> {
			Brand brand = new Brand();
			brand.setId(rs.toString());
			brand = getById(brand.getId());

			listResult.add(brand);
		});

		return listResult;
	}

}
