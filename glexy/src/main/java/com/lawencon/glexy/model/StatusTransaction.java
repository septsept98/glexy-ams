package com.lawencon.glexy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "status_transactions")
public class StatusTransaction extends BaseEntity {

	private static final long serialVersionUID = -2957305505468618480L;

	@Column(name = "code_status_tr", length = 20, nullable = false)
	private String codeStatusTr;
	
	@Column(name = "name_status_tr", length = 30, nullable = false)
	private String nameStatusTr;
	
	@ManyToOne
	@JoinColumn(name = "status_asset_id", columnDefinition = "varchar", nullable = false)
	private StatusAsset statusAssetId;

	public String getCodeStatusTr() {
		return codeStatusTr;
	}

	public void setCodeStatusTr(String codeStatusTr) {
		this.codeStatusTr = codeStatusTr;
	}

	public String getNameStatusTr() {
		return nameStatusTr;
	}

	public void setNameStatusTr(String nameStatusTr) {
		this.nameStatusTr = nameStatusTr;
	}

	public StatusAsset getStatusAssetId() {
		return statusAssetId;
	}

	public void setStatusAssetId(StatusAsset statusAssetId) {
		this.statusAssetId = statusAssetId;
	}
}
