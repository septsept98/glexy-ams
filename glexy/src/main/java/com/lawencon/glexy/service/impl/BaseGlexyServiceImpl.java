package com.lawencon.glexy.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.glexy.security.AuthPrincipal;

public class BaseGlexyServiceImpl extends BaseServiceImpl {

	@Autowired
	protected AuthPrincipal authPrincipal;

	protected String getIdAuth() throws Exception {
		if (authPrincipal.getAthentication() == null || authPrincipal.getAthentication().getPrincipal() == null) {
			throw new Exception("invalid user");
		}
		return (String) authPrincipal.getAthentication().getPrincipal();

	}

}
