package com.lawencon.elearning.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.MahasiswaDao;
import com.lawencon.elearning.model.Mahasiswa;

@Service
public class MahasiswaServiceImpl extends BaseServiceImpl implements MahasiswaService {

	@Autowired
	private MahasiswaDao mahasiswaDao;

	@Override
	public void insert(Mahasiswa data) throws Exception {
		try {
			begin();
			mahasiswaDao.insert(data);
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
		}
	}

	@Override
	public void update(Mahasiswa data) throws Exception {
		try {
			Mahasiswa mhsDb = findById(data.getId());	
			data.setCreatedAt(mhsDb.getCreatedAt());
			data.setCreatedBy(mhsDb.getCreatedBy());

			begin();
			mahasiswaDao.update(data);
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
		}
	}

	@Override
	public Mahasiswa findById(String id) throws Exception {
		return mahasiswaDao.findById(id);
	}

	@Override
	public List<Mahasiswa> findAll() throws Exception {
		return mahasiswaDao.findAll();
	}

}
