package com.lawencon.glexy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table
public class Roles extends BaseEntity {
	
	private static final long serialVersionUID = 7075030798618769100L;

	@Column(name ="name_role",length = 30, nullable = false)
	private String nameRole;
	
	@Column(length = 20, nullable = false)
	private String code;

	public String getNameRole() {
		return nameRole;
	}

	public void setNameRole(String nameRole) {
		this.nameRole = nameRole;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	

}
