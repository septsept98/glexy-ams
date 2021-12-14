package com.lawencon.glexy.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.BaseDaoImpl;
import com.lawencon.glexy.model.File;

@Repository
public class FileDaoImpl extends BaseDaoImpl<File> implements FileDao {

	@Override
	public List<File> findAll() throws Exception {
	
		return getAll();
	}

	@Override
	public File findById(String id) throws Exception {
		
		return getById(id);
	}

	@Override
	public File saveOrUpdate(File file) throws Exception {
		
		return save(file);
	}

	@Override
	public boolean deleteById(String id) throws Exception {
		
		return super.deleteById(id);
	}

	
}
