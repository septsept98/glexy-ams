package com.lawencon.glexy.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import org.springframework.stereotype.Repository;

import com.lawencon.base.BaseDaoImpl;
import com.lawencon.glexy.dao.BrandDao;
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

	@Override
	public Brand findByCode(String code) throws Exception {
		Brand brand = new Brand();
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("Select id ");
			sql.append("FROM brands ");
			sql.append("WHERE code=:code");
			
			Object result = createNativeQuery(sql.toString())
					.setParameter("code", code)
					.getSingleResult();	
			if(result != null) {
				brand.setId(result.toString());
				brand = getById(brand.getId());
			}

		}  catch (NoResultException e) {
			e.printStackTrace();
		} catch (NonUniqueResultException e) {
			e.printStackTrace();
		}
		return brand;
	}

	
	
	
	

}
