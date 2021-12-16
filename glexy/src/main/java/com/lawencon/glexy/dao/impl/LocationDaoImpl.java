package com.lawencon.glexy.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.BaseDaoImpl;
import com.lawencon.glexy.dao.LocationDao;
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
	

}
