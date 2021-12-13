package com.lawencon.glexy.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "assets")
public class Asset extends BaseEntity{
	
	private static final long serialVersionUID = 2389556928749584507L;

	private String name;
	
	private String code;
	
	@Column(name = "expired_date")
	private LocalDateTime expiredDate;
	
	@ManyToOne
	@JoinColumn(name = "asset_img", columnDefinition = "varchar")
	private File assetImg;
	
	@ManyToOne
	@JoinColumn(name = "invoice_id", columnDefinition = "varchar")
	private Invoice invoiceId;
	
	@ManyToOne
	@JoinColumn(name = "company_id", columnDefinition = "varchar")
	private Company companyId;
	
	@ManyToOne
	@JoinColumn(name = "asset_type_id", columnDefinition = "varchar")
	private AssetType assetTypeId;
	
	@ManyToOne
	@JoinColumn(name = "inventory_id", columnDefinition = "varchar")
	private Inventory inventoryId;
	
	@ManyToOne
	@JoinColumn(name = "status_asset_id", columnDefinition = "varchar")
	private StatusAsset statusAssetId;
	
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
	public LocalDateTime getExpiredDate() {
		return expiredDate;
	}
	public void setExpiredDate(LocalDateTime expiredDate) {
		this.expiredDate = expiredDate;
	}
	public Invoice getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(Invoice invoiceId) {
		this.invoiceId = invoiceId;
	}
	public Company getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Company companyId) {
		this.companyId = companyId;
	}
	public AssetType getAssetTypeId() {
		return assetTypeId;
	}
	public void setAssetTypeId(AssetType assetTypeId) {
		this.assetTypeId = assetTypeId;
	}
	public Inventory getInventoryId() {
		return inventoryId;
	}
	public void setInventoryId(Inventory inventoryId) {
		this.inventoryId = inventoryId;
	}
	public StatusAsset getStatusAssetId() {
		return statusAssetId;
	}
	public void setStatusAssetId(StatusAsset statusAssetId) {
		this.statusAssetId = statusAssetId;
	}
	public File getAssetImg() {
		return assetImg;
	}
	public void setAssetImg(File assetImg) {
		this.assetImg = assetImg;
	}
	
	
	
}
