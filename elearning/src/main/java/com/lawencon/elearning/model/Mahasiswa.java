package com.lawencon.elearning.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.lawencon.base.BaseEntity;

@Entity
public class Mahasiswa extends BaseEntity {

	private static final long serialVersionUID = -5196455701225322056L;

	private String nama;

	@ManyToOne
	@JoinColumn(name = "univ_id", columnDefinition = "varchar")
	private Universitas universitas;

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public Universitas getUniversitas() {
		return universitas;
	}

	public void setUniversitas(Universitas universitas) {
		this.universitas = universitas;
	}

}
