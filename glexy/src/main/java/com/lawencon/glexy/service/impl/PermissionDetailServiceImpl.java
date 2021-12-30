package com.lawencon.glexy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.glexy.dao.PermissionDetailDao;
import com.lawencon.glexy.dao.RolesDao;
import com.lawencon.glexy.exception.ValidationGlexyException;
import com.lawencon.glexy.model.PermissionDetail;
import com.lawencon.glexy.model.Permissions;
import com.lawencon.glexy.model.Roles;
import com.lawencon.glexy.service.PermissionDetailService;
import com.lawencon.glexy.service.PermissionsService;

@Service
public class PermissionDetailServiceImpl extends BaseGlexyServiceImpl implements PermissionDetailService {

	@Autowired
	private PermissionDetailDao permissionDetailDao;

	@Autowired
	private PermissionsService permissionsService;

	@Autowired
	private RolesDao rolesDao;

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
				validationUpdate(data);
				PermissionDetail permissionDetail = findById(data.getId());
				data.setUpdatedBy(getIdAuth());
				data.setCreatedAt(permissionDetail.getCreatedAt());
				data.setCreatedBy(permissionDetail.getCreatedBy());
				data.setVersion(permissionDetail.getVersion());
				data.setIsActive(permissionDetail.getIsActive());
			} else {
				validationSave(data);
				data.setCreatedBy("1");
			}
			Roles roles = rolesDao.findById(data.getRolesId().getId());

			if (roles == null) {
				throw new ValidationGlexyException("Roles Not Found");
			}
			data.setRolesId(roles);

			Permissions permissions = permissionsService.findById(data.getPermissionsId().getId());
			if (permissions == null) {
				throw new ValidationGlexyException("Permission Not Found");
			}
			data.setPermissionsId(permissions);

			data = permissionDetailDao.saveOrUpdate(data);

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

	@Override
	public List<PermissionDetail> findByRoleCode(String code) throws Exception {

		return permissionDetailDao.findByRoleCode(code);
	}

	@Override
	public List<PermissionDetail> findByPermissionsId(String id) throws Exception {

		return permissionDetailDao.findByPermissionsId(id);
	}

	@Override
	public void validationSave(PermissionDetail data) throws Exception {
		if (data != null) {
			if (data.getRolesId() == null || data.getPermissionsId() == null || data.getIsActive() == null) {
				throw new ValidationGlexyException("Data not Complete");
			}
		} else {
			throw new ValidationGlexyException("Data Empty");
		}
	}

	@Override
	public void validationUpdate(PermissionDetail data) throws Exception {
		if (data != null) {
			if (data.getId() != null) {
				PermissionDetail permissionDetail = findById(data.getId());
				if (permissionDetail == null) {
					throw new ValidationGlexyException("Data not Found");
				}
			} else {
				throw new ValidationGlexyException("Data not Found");
			}
			if (data.getRolesId() == null || data.getPermissionsId() == null || data.getIsActive() == null) {
				throw new ValidationGlexyException("Data not Complete");
			}
		} else {
			throw new ValidationGlexyException("Data Empty");
		}
	}

	@Override
	public PermissionDetail saveOrUpdatePermDetail(PermissionDetail data) throws Exception {
		try {
			if (data.getId() != null) {
				validationUpdate(data);
				PermissionDetail permissionDetail = findById(data.getId());
				data.setUpdatedBy(getIdAuth());
				data.setCreatedAt(permissionDetail.getCreatedAt());
				data.setCreatedBy(permissionDetail.getCreatedBy());
				data.setVersion(permissionDetail.getVersion());
				data.setIsActive(permissionDetail.getIsActive());
			} else {
				validationSave(data);
				data.setCreatedBy("1");
			}
			Roles roles = rolesDao.findById(data.getRolesId().getId());

			if (roles == null) {
				throw new ValidationGlexyException("Roles Not Found");
			}
			data.setRolesId(roles);

			Permissions permissions = permissionsService.findById(data.getPermissionsId().getId());
			if (permissions == null) {
				throw new ValidationGlexyException("Permission Not Found");
			}
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

}
