package com.lawencon.elearning.dao;

import java.util.List;

import com.lawencon.elearning.model.Mahasiswa;

public interface MahasiswaDao {

	void insert(Mahasiswa data) throws Exception;

	void update(Mahasiswa data) throws Exception;

	Mahasiswa findById(String id) throws Exception;
	
	List<Mahasiswa> findAll() throws Exception;

}
