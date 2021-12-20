package com.lawencon.glexy.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.BaseDaoImpl;
import com.lawencon.glexy.dao.PermissionDetailDao;
import com.lawencon.glexy.model.PermissionDetail;
import com.lawencon.glexy.model.Permissions;

@Repository
public class PermissionDetailDaoImpl extends BaseDaoImpl<PermissionDetail> implements PermissionDetailDao {

	@Override
	public List<PermissionDetail> findAll() throws Exception {
		
		
		
		return getAll();
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

	@Override
	public List<PermissionDetail> findByRoleId(String code) throws Exception {
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT pe.name_permission, pr.id FROM permission_role AS pr ");
		sql.append("INNER JOIN permissions AS pe ON pr.permissions_id = pe.id ");
		sql.append("INNER JOIN roles AS r ON pr.roles_id = r.id ");
		sql.append("WHERE r.code = :code");
		List<?> result = createNativeQuery(sql.toString()).setParameter("code", code)
				.getResultList();
		
		List<PermissionDetail> resultPermissionDetail = new ArrayList<>();
		
		result.forEach(rs -> {
			Object[] objArr = (Object[]) rs;
			
			PermissionDetail data = new PermissionDetail();
			
			Permissions permissions = new Permissions();
			permissions.setNamePermission(objArr[0].toString());
			data.setPermissionsId(permissions);
			data.setId(objArr[1].toString());
			resultPermissionDetail.add(data);
		});
		
		return resultPermissionDetail;
	}

}
