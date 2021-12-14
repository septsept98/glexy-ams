package com.lawencon.glexy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.glexy.dao.LocationDao;
import com.lawencon.glexy.model.Location;

public class LocationServiceImpl extends BaseServiceImpl implements LocationService {

	@Autowired
	private LocationDao locationDao;
	
	@Override
	public Location saveOrUpdate(Location data) throws Exception {
		
		try {
			if(data.getId() != null) {
				Location location = findById(data.getId());
				data.setCreatedAt(location.getCreatedAt());
				data.setCreatedBy(location.getCreatedBy());
				data.setVersion(location.getVersion());
			}
			
			begin();
			data = locationDao.saveOrUpdate(data);
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
		}
		return data;
		
	}

	@Override
	public Location findById(String id) throws Exception {
		return locationDao.findById(id);
	}

	@Override
	public List<Location> findAll() throws Exception {
		return locationDao.findAll();
	}

	@Override
	public boolean removeById(String id) throws Exception {
		boolean result = false;
		try {
			begin();
			result = locationDao.removeById(id);
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
		}
		return result;
	}
	
	

}
