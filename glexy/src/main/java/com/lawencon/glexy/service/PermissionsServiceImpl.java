package com.lawencon.glexy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.glexy.dao.PermissionsDao;
import com.lawencon.glexy.model.Permissions;

@Service
public class PermissionsServiceImpl extends BaseServiceImpl implements PermissionsService {
	
	@Autowired
	private PermissionsDao permissionsDao;
	
	@Override
	public List<Permissions> findAll() throws Exception {
		
		return permissionsDao.findAll();
	}

	@Override
	public Permissions findById(String id) throws Exception {
		
		return permissionsDao.findById(id);
	}

	@Override
	public Permissions saveOrUpdate(Permissions data) throws Exception {
	
		try {
			if (data.getId() != null) {
				Permissions permissions = findById(data.getId());
				data.setCode(permissions.getCode()); 
				data.setCreatedAt(permissions.getCreatedAt());
				data.setCreatedBy(permissions.getCreatedBy());
				data.setVersion(permissions.getVersion());
				data.setIsActive(permissions.getIsActive());
			}

			begin();
			data = permissionsDao.saveOrUpdate(data);
			commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
		}
		return data;
	}

	@Override
	public boolean deleteById(String id) throws Exception {
		
		boolean result = false;
		try {
			begin();
			result = permissionsDao.deleteById(id);
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new Exception(e);
		}
		return result;
	}

	
	
}
