package com.lawencon.glexy.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "transaction_details")
public class TransactionDetail extends BaseEntity {
	
	private static final long serialVersionUID = 280099300793821086L;

	@ManyToOne
	@JoinColumn(name = "transaction_id", columnDefinition = "varchar")
	private Transactions transactionId;
	
	@Column(name = "duration_date")
	private LocalDate durationDate;
	
	@ManyToOne
	@JoinColumn(name = "asset_id", columnDefinition = "varchar")
	private Asset assetId;
	
	@ManyToOne
	@JoinColumn(name = "status_asset_checkout_id", columnDefinition = "varchar")
	private StatusAsset statusAssetCheckoutId;

	@Column(name = "date_checkin")
	private LocalDate dateCheckin;

	@Column(name = "status_email")
	private Boolean statusEmail;

	@ManyToOne
	@JoinColumn(name = "status_tr_checkin_id", columnDefinition = "varchar")
	private StatusTransaction statusTrCheckinId;

	public Transactions getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Transactions transactionId) {
		this.transactionId = transactionId;
	}

	public LocalDate getDurationDate() {
		return durationDate;
	}

	public void setDurationDate(LocalDate durationDate) {
		this.durationDate = durationDate;
	}

	public Asset getAssetId() {
		return assetId;
	}

	public void setAssetId(Asset assetId) {
		this.assetId = assetId;
	}

	public StatusAsset getStatusAssetCheckoutId() {
		return statusAssetCheckoutId;
	}

	public void setStatusAssetCheckoutId(StatusAsset statusAssetCheckoutId) {
		this.statusAssetCheckoutId = statusAssetCheckoutId;
	}

	public LocalDate getDateCheckin() {
		return dateCheckin;
	}

	public void setDateCheckin(LocalDate dateCheckin) {
		this.dateCheckin = dateCheckin;
	}

	public Boolean getStatusEmail() {
		return statusEmail;
	}

	public void setStatusEmail(Boolean statusEmail) {
		this.statusEmail = statusEmail;
	}

	public StatusTransaction getStatusTrCheckinId() {
		return statusTrCheckinId;
	}

	public void setStatusTrCheckinId(StatusTransaction statusTrCheckinId) {
		this.statusTrCheckinId = statusTrCheckinId;
	}
	
}
