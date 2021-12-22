package com.lawencon.glexy.service.impl;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.glexy.dao.LocationDao;
import com.lawencon.glexy.model.Location;
import com.lawencon.glexy.service.LocationService;

@Service
public class LocationServiceImpl extends BaseGlexyServiceImpl implements LocationService {

	@Autowired
	private LocationDao locationDao;
	
	@Override
	public Location saveOrUpdate(Location data) throws Exception {
		
		try {
			if(data.getId() != null) {
				Location location = findById(data.getId());
				data.setCode(location.getCode());
				data.setCreatedAt(location.getCreatedAt());
				data.setCreatedBy(location.getCreatedBy());
				data.setUpdatedBy(getIdAuth());
				data.setVersion(location.getVersion());
			} else {
				data.setCreatedBy(getIdAuth());
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
		Location result = new Location();
		try {
			result = locationDao.findById(id);
		} catch (NoResultException e) {
			e.printStackTrace();
			throw new NoResultException("Location not found");
		}
		return result;
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
