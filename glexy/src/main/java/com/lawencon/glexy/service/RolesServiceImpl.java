package com.lawencon.glexy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.glexy.dao.RolesDao;
import com.lawencon.glexy.dto.roles.RolesInsertReqDto;
import com.lawencon.glexy.model.PermissionDetail;
import com.lawencon.glexy.model.Roles;

@Service
public class RolesServiceImpl extends BaseServiceImpl implements RolesService  {
	
	@Autowired
	private RolesDao rolesDao;
	
	@Autowired
	private PermissionDetailService permissionDetailService;
	
	@Override
	public List<Roles> findAll() throws Exception {
		
		return rolesDao.findAll();
	}

	@Override
	public Roles findById(String id) throws Exception {
		
		return rolesDao.findById(id);
	}

	@Override
	public Roles saveOrUpdate(RolesInsertReqDto data) throws Exception {
		
		try {
			if (data.getRoles().getId() != null) {
				Roles roles = findById(data.getRoles().getId());
				roles.setNameRole(data.getRoles().getNameRole());
				data.setRoles(roles);
			}

			begin();
			Roles rolesNew = rolesDao.saveOrUpdate(data.getRoles());
			if (data.getRoles().getId() == null) {
			for(int i = 0;i<data.getData().size();i++) {
				data.getData().get(i).setRolesId(rolesNew);
				permissionDetailService.saveOrUpdate(data.getData().get(i));
			}}
			commit();
			return data.getRoles();
			
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new Exception(e);
		}
		
	}

	@Override
	public boolean deleteById(String id) throws Exception {
		boolean result = false;
		try {
			begin();
			result = rolesDao.deleteById(id);
			commit();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new Exception(e);
		}
		
	}

	
}
