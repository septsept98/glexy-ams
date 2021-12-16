package com.lawencon.glexy.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;

import com.lawencon.glexy.security.AuthPrincipal;

public class BaseServiceImpl extends com.lawencon.base.BaseServiceImpl {

	@Autowired
	protected AuthPrincipal authPrincipal;

	protected String getIdAuth() throws Exception {
		if (authPrincipal.getAthentication() == null || authPrincipal.getAthentication().getPrincipal() == null) {
			throw new Exception("invalid user");
		}
		return (String) authPrincipal.getAthentication().getPrincipal();

	}

}
