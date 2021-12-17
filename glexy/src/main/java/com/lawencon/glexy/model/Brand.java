package com.lawencon.glexy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "brands")
public class Brand extends BaseEntity {

	private static final long serialVersionUID = -2221432347755352384L;

	@Column(length = 30, nullable = false)
	private String names;
	
	@Column(length = 20, nullable = false)
	private String code;

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
}
