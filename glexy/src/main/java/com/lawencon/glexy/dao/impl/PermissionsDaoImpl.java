package com.lawencon.glexy.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.BaseDaoImpl;
import com.lawencon.glexy.dao.PermissionsDao;
import com.lawencon.glexy.model.Permissions;

@Repository
public class PermissionsDaoImpl extends BaseDaoImpl<Permissions> implements PermissionsDao {

	@Override
	public List<Permissions> findAll() throws Exception {
		
		return getAll();
	}

	@Override
	public Permissions findById(String id) throws Exception {
		
		return getById(id);
	}

	@Override
	public Permissions saveOrUpdate(Permissions permissions) throws Exception {
		
		return save(permissions);
	}

	@Override
	public boolean deleteById(String id) throws Exception {
	
		return super.deleteById(id);
	}

}
