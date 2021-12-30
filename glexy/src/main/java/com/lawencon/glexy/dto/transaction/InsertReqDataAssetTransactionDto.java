package com.lawencon.glexy.dto.transaction;

public class InsertReqDataAssetTransactionDto {

	private String inventId;

	private Integer qty;

	private String brandId;

	public String getInventId() {
		return inventId;
	}

	public void setInventId(String inventId) {
		this.inventId = inventId;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

}
