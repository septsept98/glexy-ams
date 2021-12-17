package com.lawencon.glexy.dto.asset;

import java.math.BigDecimal;
import java.time.LocalDate;

public class InsertReqDataHelperAssetDto {
	
	private String nameAsset;
	
	private int qty;
	
	private String codeInventory;
	
	private String nameBrand;
	
	private String nameAssetType;
	
	private String codeInvoice;
	
	private BigDecimal totalPrice;
	
	private LocalDate expiredDate;
	
	private String nameStatusAsset;
	
	private String companyName;
	
	public String getNameAsset() {
		return nameAsset;
	}
	public void setNameAsset(String nameAsset) {
		this.nameAsset = nameAsset;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public String getCodeInventory() {
		return codeInventory;
	}
	public void setCodeInventory(String codeInventory) {
		this.codeInventory = codeInventory;
	}
	public String getNameBrand() {
		return nameBrand;
	}
	public void setNameBrand(String nameBrand) {
		this.nameBrand = nameBrand;
	}
	public String getNameAssetType() {
		return nameAssetType;
	}
	public void setNameAssetType(String nameAssetType) {
		this.nameAssetType = nameAssetType;
	}
	public String getCodeInvoice() {
		return codeInvoice;
	}
	public void setCodeInvoice(String codeInvoice) {
		this.codeInvoice = codeInvoice;
	}
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	public LocalDate getExpiredDate() {
		return expiredDate;
	}
	public void setExpiredDate(LocalDate expiredDate) {
		this.expiredDate = expiredDate;
	}
	public String getNameStatusAsset() {
		return nameStatusAsset;
	}
	public void setNameStatusAsset(String nameStatusAsset) {
		this.nameStatusAsset = nameStatusAsset;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	

}
