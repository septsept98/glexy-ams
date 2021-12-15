package com.lawencon.glexy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.glexy.dao.PermissionDetailDao;
import com.lawencon.glexy.model.PermissionDetail;
import com.lawencon.glexy.model.Permissions;
import com.lawencon.glexy.model.Roles;

@Service
public class PermissionDetailServiceImpl extends BaseServiceImpl implements PermissionDetailService {

	@Autowired
	private PermissionDetailDao permissionDetailDao;

	@Autowired
	private RolesService rolesService;

	@Autowired
	private PermissionsService permissionsService;

	@Override
	public List<PermissionDetail> findAll() throws Exception {

		return permissionDetailDao.findAll();
	}

	@Override
	public PermissionDetail findById(String id) throws Exception {

		return permissionDetailDao.findById(id);
	}

	@Override
	public PermissionDetail saveOrUpdate(PermissionDetail data) throws Exception {

		try {
			if (data.getId() != null) {
				PermissionDetail permissionDetail = findById(data.getId());
				data.setCreatedAt(permissionDetail.getCreatedAt());
				data.setCreatedBy(permissionDetail.getCreatedBy());
				data.setVersion(permissionDetail.getVersion());
				data.setIsActive(permissionDetail.getIsActive());
			}
			Roles roles = rolesService.findById(data.getRolesId().getId());
			data.setRolesId(roles);

			Permissions permissions = permissionsService.findById(data.getPermissionsId().getId());
			data.setPermissionsId(permissions);

			begin();
			data = permissionDetailDao.saveOrUpdate(data);
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
			result = permissionDetailDao.deleteById(id);
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new Exception(e);
		}
		return result;
	}

	@Override
	public List<PermissionDetail> findByRoleId(String id) throws Exception {

		return permissionDetailDao.findByRoleId(id);
	}

}