package com.lawencon.glexy.dto.roles;

import java.util.List;

import com.lawencon.glexy.model.PermissionDetail;
import com.lawencon.glexy.model.Roles;

public class RolesInsertReqDto {
	
	private Roles roles;
	private List<PermissionDetail> data;
	
	public Roles getRoles() {
		return roles;
	}
	public void setRoles(Roles roles) {
		this.roles = roles;
	}
	public List<PermissionDetail> getData() {
		return data;
	}
	public void setData(List<PermissionDetail> data) {
		this.data = data;
	}
	
}
