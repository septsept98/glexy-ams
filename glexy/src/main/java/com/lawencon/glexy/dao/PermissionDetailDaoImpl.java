package com.lawencon.glexy.dao;

import java.util.List;

import com.lawencon.base.BaseDaoImpl;
import com.lawencon.glexy.model.PermissionDetail;

public class PermissionDetailDaoImpl extends BaseDaoImpl<PermissionDetail> implements PermissionDetailDao {

	@Override
	public List<PermissionDetail> findAll() throws Exception {
		
		
		
		return ;
	}

	@Override
	public PermissionDetail findById(String id) throws Exception {
		
		return getById(id);
	}

	@Override
	public PermissionDetail saveOrUpdate(PermissionDetail permissionDetail) throws Exception {
		
		return save(permissionDetail);
	}

	@Override
	public boolean deleteById(String id) throws Exception {
		
		return super.deleteById(id);
	}

}
