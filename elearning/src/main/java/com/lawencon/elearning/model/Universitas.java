package com.lawencon.elearning.model;

import javax.persistence.Entity;

import com.lawencon.base.BaseEntity;

@Entity
public class Universitas extends BaseEntity {

	private static final long serialVersionUID = -9034083546988015483L;

	private String nama;

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

}
