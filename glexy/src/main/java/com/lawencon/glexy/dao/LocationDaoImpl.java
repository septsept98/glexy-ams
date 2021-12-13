package com.lawencon.glexy.dao;

import java.util.List;

import com.lawencon.base.BaseDaoImpl;
import com.lawencon.glexy.model.Location;

public class LocationDaoImpl extends BaseDaoImpl<Location> implements LocationDao {

	@Override
	public void insert(Location data) throws Exception {
		save(data);		
	}

	@Override
	public void update(Location data) throws Exception {
		save(data);		
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
