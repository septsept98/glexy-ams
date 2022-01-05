package com.lawencon.glexy.helper;

import java.time.LocalDate;

public class ReportDataExpiredAsset {

	private byte[] image;
	private String idAsset;
	private String codeAsset;
	private String nameAsset;
	private String brand;
	private String typeAsset;
	private String statusAsset;
	private LocalDate expiredDate;

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getIdAsset() {
		return idAsset;
	}

	public void setIdAsset(String idAsset) {
		this.idAsset = idAsset;
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
