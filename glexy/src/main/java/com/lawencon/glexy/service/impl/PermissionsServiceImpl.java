package com.lawencon.glexy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.glexy.dao.PermissionDetailDao;
import com.lawencon.glexy.dao.PermissionsDao;
import com.lawencon.glexy.exception.ValidationGlexyException;
import com.lawencon.glexy.model.PermissionDetail;
import com.lawencon.glexy.model.Permissions;
import com.lawencon.glexy.model.Users;
import com.lawencon.glexy.service.PermissionDetailService;
import com.lawencon.glexy.service.PermissionsService;

@Service
public class PermissionsServiceImpl extends BaseGlexyServiceImpl implements PermissionsService {
	
	@Autowired
	private PermissionsDao permissionsDao;
	
	@Autowired
	private PermissionDetailDao permissionDetailDao;
	
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
				data.setUpdatedBy(getIdAuth());
				data.setVersion(permissions.getVersion());
				data.setIsActive(permissions.getIsActive());
			}else {
				data.setUpdatedBy(getIdAuth());
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
			validationFk(id);
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

	@Override
	public void validationFk(String id) throws Exception {
		List<PermissionDetail> dataPermissionDetail = permissionDetailDao.findByPermissionsId(id);
		
		if (dataPermissionDetail != null) {

			throw new ValidationGlexyException("Permission in Use");
		}
		
	}

	
	
}
