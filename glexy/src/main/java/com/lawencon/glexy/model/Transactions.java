package com.lawencon.glexy.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table
public class Transactions extends BaseEntity{

	private static final long serialVersionUID = 8439264959061000619L;

	@Column(name = "code_transaction", length = 20, nullable = false)
	private String codeTransaction;
	
	@Column(name = "checkout_date", nullable = false)
	private LocalDate checkOutDate;
	
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "employee_id", columnDefinition = "varchar")
	private Employee employeeId;
	
	@ManyToOne
	@JoinColumn(name = "location_id", columnDefinition = "varchar")
	private Location locationId;
	
	@ManyToOne
	@JoinColumn(name = "asset_general_id", columnDefinition = "varchar")
	private Asset assetGeneralId;
	
	@ManyToOne
	@JoinColumn(name = "users_id", columnDefinition = "varchar")
	private Users userId;

	public String getCodeTransaction() {
		return codeTransaction;
	}

	public void setCodeTransaction(String codeTransaction) {
		this.codeTransaction = codeTransaction;
	}

	public LocalDate getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(LocalDate checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Employee getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Employee employeeId) {
		this.employeeId = employeeId;
	}

	public Location getLocationId() {
		return locationId;
	}

	public void setLocationId(Location locationId) {
		this.locationId = locationId;
	}

	public Asset getAssetGeneralId() {
		return assetGeneralId;
	}

	public void setAssetGeneralId(Asset assetGeneralId) {
		this.assetGeneralId = assetGeneralId;
	}

	public Users getUserId() {
		return userId;
	}

	public void setUserId(Users userId) {
		this.userId = userId;
	}
	
}
