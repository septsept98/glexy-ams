package com.lawencon.glexy.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.BaseDaoImpl;
import com.lawencon.glexy.dao.LocationDao;
import com.lawencon.glexy.model.Company;
import com.lawencon.glexy.model.Location;

@Repository
public class LocationDaoImpl extends BaseDaoImpl<Location> implements LocationDao {

	@Override
	public Location saveOrUpdate(Location data) throws Exception {
		return save(data);
	}

	@Override
	public boolean removeById(String id) throws Exception {
		return deleteById(id);
	}

	@Override
	public Location findById(String id) throws Exception {
		return getById(id);
	}

	@Override
	public List<Location> findAll() throws Exception {
		return getAll();
	}

	@Override
	public List<Location> findByCompanyId(String id) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT company_id FROM locations ");
		sql.append("WHERE company_id = :id");
		List<?> result = createNativeQuery(sql.toString()).setParameter("id", id).getResultList();

		List<Location> resultLocation= new ArrayList<>();

		result.forEach(rs -> {

			Location data = new Location();

			Company company = new Company();
			company.setId(rs.toString());
			data.setCompanyId(company);
			resultLocation.add(data);
		});

		return resultLocation;
	}

	@Override
	public List<Location> search(String search) throws Exception {
		List<Location> listResult = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("Select id ");
		sql.append("FROM locations ");
		sql.append("WHERE lower(name_place) LIKE lower('%" + search + "%') OR lower(code) LIKE lower('%" + search + "%')");

		List<?> result = createNativeQuery(sql.toString()).getResultList();

		result.forEach(rs -> {
			Location location = new Location();
			location.setId(rs.toString());
			location = getById(location.getId());

			listResult.add(location);
		});

		return listResult;
	}
	
	

}
