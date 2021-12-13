package com.lawencon.glexy.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.BaseDaoImpl;
import com.lawencon.glexy.model.Roles;

@Repository
public class RolesDaoImpl extends BaseDaoImpl<Roles> implements RolesDao {

	@Override
	public List<Roles> findAll() throws Exception {
		
		return getAll();
	}

	@Override
	public Roles findById(String id) throws Exception {
		
		return getById(id);
	}

	@Override
	public Roles saveOrUpdate(Roles roles) throws Exception {
		
		return save(roles);
	}

	@Override
	public boolean deleteById(String id) throws Exception {
	
		return super.deleteById(id);
	}

}
