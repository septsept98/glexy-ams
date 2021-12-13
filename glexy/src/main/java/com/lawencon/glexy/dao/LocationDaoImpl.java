package com.lawencon.glexy.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.BaseDaoImpl;
import com.lawencon.glexy.model.Location;

@Repository
public class LocationDaoImpl extends BaseDaoImpl<Location> implements LocationDao {

	@Override
	public Location saveOrUpdate(Location data) throws Exception {
		return save(data);
	}

	@Override
	public boolean deleteById(String id) throws Exception {
		return super.deleteById(id);
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
