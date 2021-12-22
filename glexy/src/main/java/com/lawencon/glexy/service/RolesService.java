package com.lawencon.glexy.service;

import java.util.List;

import com.lawencon.glexy.dto.DeleteResDto;
import com.lawencon.glexy.dto.roles.RolesInsertReqDto;
import com.lawencon.glexy.model.Permissions;
import com.lawencon.glexy.model.Roles;

public interface RolesService {

	List<Roles> findAll() throws Exception;

	Roles findById(String id) throws Exception;

	Roles saveOrUpdate(RolesInsertReqDto data) throws Exception;

	boolean deleteById(String id) throws Exception;

	void validationFk(String id) throws Exception;

	void validationSave(Roles data) throws Exception;

	void validationUpdate(Roles data) throws Exception;

}
