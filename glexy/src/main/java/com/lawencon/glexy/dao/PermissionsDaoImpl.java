package com.lawencon.glexy.dao;

import java.util.List;

import com.lawencon.base.BaseDaoImpl;
import com.lawencon.glexy.model.Permissions;

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
