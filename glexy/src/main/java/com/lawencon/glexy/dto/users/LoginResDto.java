package com.lawencon.glexy.dto.users;

import java.util.List;

import com.lawencon.glexy.model.PermissionDetail;

public class LoginResDto {

	private String rolesCode;
	
	private String token;

	public String getRolesCode() {
		return rolesCode;
	}

	public void setRolesCode(String rolesCode) {
		this.rolesCode = rolesCode;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
}
