package com.lawencon.glexy.service.impl;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.glexy.dao.LocationDao;
import com.lawencon.glexy.dao.TransactionDao;
import com.lawencon.glexy.exception.ValidationGlexyException;
import com.lawencon.glexy.model.Asset;
import com.lawencon.glexy.model.Invoice;
import com.lawencon.glexy.model.Location;
import com.lawencon.glexy.model.Transactions;
import com.lawencon.glexy.service.LocationService;

@Service
public class LocationServiceImpl extends BaseServiceImpl implements LocationService {

	@Autowired
	private LocationDao locationDao;
	
	@Autowired
	private TransactionDao transactionDao;
	
	@Override
	public Location saveOrUpdate(Location data) throws Exception {
		
		try {
			if(data.getId() != null) {
				validationUpdate(data);
				Location location = findById(data.getId());
				data.setCode(location.getCode());
				data.setCreatedAt(location.getCreatedAt());
				data.setCreatedBy(location.getCreatedBy());
				data.setUpdatedBy("1");
				data.setVersion(location.getVersion());
			} else {
				validationSave(data);
				data.setCreatedBy("3");
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
			validationFk(id);
			begin();
			result = locationDao.removeById(id);
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
		}
		return result;
	}

	@Override
	public void validationFk(String id) throws Exception {
		List<Transactions> dataEmployee = transactionDao.findByLocationId(id);
		if (dataEmployee != null) {

			throw new ValidationGlexyException("Location Type in Use");
		}
		
	}

	@Override
	public void validationSave(Location data) throws Exception {
		if(data.getCode() == null || data.getNamePlace() == null || data.getCompanyId() == null) {
			throw new ValidationGlexyException("Data not Complete");
		}
		
	}

	@Override
	public void validationUpdate(Location data) throws Exception {
		if (data.getId() != null) {
			Location location = findById(data.getId());
			if (location == null) {
				throw new ValidationGlexyException("Data not Found");
			}
		} else {
			throw new ValidationGlexyException("Data not Found");
		}if(data.getCode() == null || data.getNamePlace() == null || data.getCompanyId() == null) {
			throw new ValidationGlexyException("Data not Complete");
		}
		
	}
	
	

}
