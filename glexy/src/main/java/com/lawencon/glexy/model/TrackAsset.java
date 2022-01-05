package com.lawencon.glexy.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "track_assets")
public class TrackAsset extends BaseEntity {

	private static final long serialVersionUID = 1251049900399795603L;

	@Column(name = "code_asset", length = 20, nullable = false)
	private String codeAsset;

	@Column(name = "name_activity", length = 30, nullable = false)
	private String nameActivity;

	@Column(name = "date_activity", nullable = false)
	private LocalDate dateActivity;

	@Column(name = "user_id", length = 36, nullable = false)
	private String userId;

	@Column(name = "transaction_code", length = 20)
	private String transactionCode;

	public String getCodeAsset() {
		return codeAsset;
	}

	public void setCodeAsset(String codeAsset) {
		this.codeAsset = codeAsset;
	}

	public String getNameActivity() {
		return nameActivity;
	}

	public void setNameActivity(String nameActivity) {
		this.nameActivity = nameActivity;
	}

	public LocalDate getDateActivity() {
		return dateActivity;
	}

	public void setDateActivity(LocalDate dateActivity) {
		this.dateActivity = dateActivity;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}
	
}
