package com.lawencon.glexy.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "companies")
public class Company extends BaseEntity{

	private static final long serialVersionUID = -7368437893815303686L;

	private String name;
	
	private String code;
	
	private String description;
	
	private String address;
	
	@ManyToOne
	@JoinColumn(name = "company_img", columnDefinition = "varchar")
	private File company_img;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public File getCompany_img() {
		return company_img;
	}
	public void setCompany_img(File company_img) {
		this.company_img = company_img;
	}
	
	
}
