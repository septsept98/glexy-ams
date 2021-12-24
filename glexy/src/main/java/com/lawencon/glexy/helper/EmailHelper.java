package com.lawencon.glexy.helper;

import java.time.LocalDate;

public class EmailHelper {

	private String valueName;
	private String employeeName;
	private LocalDate expiredDate;
	private String fileName;
	private byte[] attach;

	public String getValueName() {
		return valueName;
	}

	public void setValueName(String valueName) {
		this.valueName = valueName;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public LocalDate getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(LocalDate expiredDate) {
		this.expiredDate = expiredDate;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getAttach() {
		return attach;
	}

	public void setAttach(byte[] attach) {
		this.attach = attach;
	}

}
