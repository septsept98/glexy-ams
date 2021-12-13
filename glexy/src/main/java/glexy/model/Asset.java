package glexy.model;

import java.sql.Date;

public class Asset {
	
	private String name;
	private String code;
	private Date expiredDate;
	private Invoice invoiceId;
	private Company companyId;
	private AssetType assetTypeId;
	private Inventory inventoryId;
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
	public Date getExpiredDate() {
		return expiredDate;
	}
	public void setExpiredDate(Date expiredDate) {
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
	
	
}
