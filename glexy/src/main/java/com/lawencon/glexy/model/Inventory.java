package com.lawencon.glexy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "inventories")
public class Inventory extends BaseEntity{
	
	private static final long serialVersionUID = -6744583972264191520L;

	@Column(name = "name_asset", length = 30, nullable = false)
	private String nameAsset;
	
	@Column(length = 20, nullable = false)
	private String code;
	
	@Column(nullable = false)
	private Integer stock;
	
	@Column(name = "latest_stock", nullable = false)
	private Integer latestStock;
	
	public String getNameAsset() {
		return nameAsset;
	}
	public void setNameAsset(String nameAsset) {
		this.nameAsset = nameAsset;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public Integer getLatestStock() {
		return latestStock;
	}
	public void setLatestStock(Integer latestStock) {
		this.latestStock = latestStock;
	}
	
	
	
}
