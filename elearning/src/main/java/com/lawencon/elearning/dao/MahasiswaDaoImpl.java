package com.lawencon.elearning.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.BaseDaoImpl;
import com.lawencon.elearning.model.Mahasiswa;

@Repository
public class MahasiswaDaoImpl extends BaseDaoImpl<Mahasiswa> implements MahasiswaDao {

	@Override
	public void insert(Mahasiswa data) throws Exception {
		save(data);
	}

	@Override
	public void update(Mahasiswa data) throws Exception {
		save(data);
	}

	@Override
	public Mahasiswa findById(String id) throws Exception {
		return getById(id);
	}
	
	@Override
	public List<Mahasiswa> findAll() throws Exception {
		return getAll();
	}

}
