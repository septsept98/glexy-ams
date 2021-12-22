package com.lawencon.glexy.helper;

import java.time.LocalDate;

public class ReportDataExpiredAsset {

	private String image;
	private String codeAsset;
	private String nameAsset;
	private String brand;
	private String typeAsset;
	private String statusAsset;
	private LocalDate expiredDate;

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getCodeAsset() {
		return codeAsset;
	}

	public void setCodeAsset(String codeAsset) {
		this.codeAsset = codeAsset;
	}

	public String getNameAsset() {
		return nameAsset;
	}

	public void setNameAsset(String nameAsset) {
		this.nameAsset = nameAsset;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getTypeAsset() {
		return typeAsset;
	}

	public void setTypeAsset(String typeAsset) {
		this.typeAsset = typeAsset;
	}

	public String getStatusAsset() {
		return statusAsset;
	}

	public void setStatusAsset(String statusAsset) {
		this.statusAsset = statusAsset;
	}

	public LocalDate getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(LocalDate expiredDate) {
		this.expiredDate = expiredDate;
	}

}
