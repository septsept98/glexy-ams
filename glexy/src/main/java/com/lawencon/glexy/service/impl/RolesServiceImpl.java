package com.lawencon.glexy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.glexy.dao.RolesDao;
import com.lawencon.glexy.dao.UsersDao;
import com.lawencon.glexy.dto.roles.RolesInsertReqDto;
import com.lawencon.glexy.exception.ValidationGlexyException;
import com.lawencon.glexy.model.PermissionDetail;
import com.lawencon.glexy.model.Roles;
import com.lawencon.glexy.model.Users;
import com.lawencon.glexy.service.PermissionDetailService;
import com.lawencon.glexy.service.RolesService;

@Service
public class RolesServiceImpl extends BaseGlexyServiceImpl implements RolesService {

	@Autowired
	private RolesDao rolesDao;

	@Autowired
	private PermissionDetailService permissionDetailService;

	@Autowired
	private UsersDao usersDao;

	@Override
	public List<Roles> findAll() throws Exception {
		List<Roles> listRoles = rolesDao.findAll();
		for(int i = 0; i<listRoles.size();i++) {
			if(listRoles.get(i).getCode().equals("SA")) {
				listRoles.remove(i);
			}
		}
		for(int i = 0; i<listRoles.size();i++) {
			if(listRoles.get(i).getCode().equals("S")) {
				listRoles.remove(i);
			}
		}
		return listRoles;
	}

	@Override
	public Roles findById(String id) throws Exception {

		return rolesDao.findById(id);
	}

	@Override
	public Roles saveOrUpdate(RolesInsertReqDto data) throws Exception {

		try {
			if (data.getRoles().getId() != null) {
				validationUpdate(data.getRoles());
				Roles roles = findById(data.getRoles().getId());
				roles.setNameRole(data.getRoles().getNameRole());
				roles.setUpdatedBy("1");
				data.setRoles(roles);

			} else {
				validationSave(data.getRoles());
				Roles roles = new Roles();
				roles.setNameRole(data.getRoles().getNameRole());
				roles.setCode(data.getRoles().getCode());
				roles.setIsActive(data.getRoles().getIsActive());
				roles.setCreatedBy("1");
				data.setRoles(roles);
			}

			begin();
			Roles rolesNew = rolesDao.saveOrUpdate(data.getRoles());

			for (int i = 0; i < data.getData().size(); i++) {
				data.getData().get(i).setRolesId(rolesNew);

				permissionDetailService.saveOrUpdate(data.getData().get(i));
			}
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

			validationFk(id);
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

	@Override
	public void validationFk(String id) throws Exception {

		List<PermissionDetail> dataPermissionDetail = permissionDetailService.findByRoleId(id);
		List<Users> dataUsers = usersDao.findByRolesId(id);
		if (dataPermissionDetail.size() != 0 || dataUsers.size() != 0) {

			throw new ValidationGlexyException("Roles in Use");
		}

	}

	@Override
	public void validationSave(Roles data) throws Exception {
		if (data != null) {
			if (data.getCode().isBlank() || data.getNameRole().isBlank()  || data.getIsActive() == null) {

				throw new ValidationGlexyException("Data not Complete");

			}
		} else {
			throw new ValidationGlexyException("Data Empty");
		}
	}

	@Override
	public void validationUpdate(Roles data) throws Exception {
		if (data != null) {
			if (data.getId() != null) {
				Roles role = findById(data.getId());
				if (role == null) {
					throw new ValidationGlexyException("Data not Found");
				}
			} else {
				throw new ValidationGlexyException("Data not Found");
			}

			if (data.getCode() == null || data.getNameRole() == null || data.getIsActive() == null) {

				throw new ValidationGlexyException("Data not Complete");

			}
		} else {
			throw new ValidationGlexyException("Data Empty");
		}
	}

}
