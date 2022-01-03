package com.lawencon.glexy.helper;

import java.time.LocalDate;

public class ReportDataTransactionOutDate {

	private String codeTrx;
	private String nip;
	private String employeeName;
	private String email;
	private String codeAsset;
	private String nameAsset;
	private byte[] image;
	private LocalDate dueDate;
	private LocalDate checkinDate;

	public String getCodeTrx() {
		return codeTrx;
	}

	public void setCodeTrx(String codeTrx) {
		this.codeTrx = codeTrx;
	}

	public String getNip() {
		return nip;
	}

	public void setNip(String nip) {
		this.nip = nip;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public LocalDate getCheckinDate() {
		return checkinDate;
	}

	public void setCheckinDate(LocalDate checkinDate) {
		this.checkinDate = checkinDate;
	}

}
