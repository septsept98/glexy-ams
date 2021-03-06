package com.lawencon.glexy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "employees")
public class Employee extends BaseEntity {

	private static final long serialVersionUID = -4424003479945494654L;

	@Column(name ="name_employee",length = 30, nullable = false)
	private String nameEmployee;
	
	@Column(length = 20, nullable = false)
	private String nip;
	
	@Column(name ="email_employee",length = 30)
	private String emailEmployee;
	
	@Column(name ="phone_number",length = 20, nullable = false)
	private String phoneNumber;
	
	@Column(length = 10, nullable = false)
	private String gender;
	
	@ManyToOne
	@JoinColumn(name = "company_id", columnDefinition = "varchar")
	private Company companyId;

	public String getNameEmployee() {
		return nameEmployee;
	}

	public void setNameEmployee(String nameEmployee) {
		this.nameEmployee = nameEmployee;
	}

	public String getNip() {
		return nip;
	}

	public void setNip(String nip) {
		this.nip = nip;
	}

	public String getEmailEmployee() {
		return emailEmployee;
	}

	public void setEmailEmployee(String emailEmployee) {
		this.emailEmployee = emailEmployee;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Company getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Company companyId) {
		this.companyId = companyId;
	}

}
