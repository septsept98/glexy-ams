package com.lawencon.glexy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.glexy.dao.RolesDao;
import com.lawencon.glexy.model.Roles;

@Service
public class RolesServiceImpl extends BaseServiceImpl implements RolesService  {
	
	@Autowired
	private RolesDao rolesDao;
	
	@Override
	public List<Roles> findAll() throws Exception {
		
		return rolesDao.findAll();
	}

	@Override
	public Roles findById(String id) throws Exception {
		
		return rolesDao.findById(id);
	}

	@Override
	public Roles saveOrUpdate(Roles data) throws Exception {
		
		try {
			if (data.getId() != null) {
				Roles roles = findById(data.getId());
				data.setCode(roles.getCode()); 
				data.setCreatedAt(roles.getCreatedAt());
				data.setCreatedBy(roles.getCreatedBy());
				data.setVersion(roles.getVersion());
				data.setIsActive(roles.getIsActive());
			}

			begin();
			data = rolesDao.saveOrUpdate(data);
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
			result = rolesDao.deleteById(id);
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
		}
		return result;
	}

	
}
