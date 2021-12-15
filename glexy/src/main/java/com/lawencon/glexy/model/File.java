package com.lawencon.glexy.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "files")
public class File extends BaseEntity {

	private static final long serialVersionUID = 5892756716165668671L;

	private byte[] file;
	
	private String extension;

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}
	
	
}
