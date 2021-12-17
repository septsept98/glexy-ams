package com.lawencon.glexy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "locations")
public class Location extends BaseEntity{
	
	private static final long serialVersionUID = 4995297169641303960L;
	
	@Column(name = "name_place", length = 30, nullable = false)
	private String namePlace;
	
	@Column(length = 20, nullable = false)
	private String code;
	
	@ManyToOne
	@JoinColumn(name = "company_id", columnDefinition = "varchar")
	private Company companyId;
	
	public String getNamePlace() {
		return namePlace;
	}
	public void setNamePlace(String namePlace) {
		this.namePlace = namePlace;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Company getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Company companyId) {
		this.companyId = companyId;
	}
	
	
}
