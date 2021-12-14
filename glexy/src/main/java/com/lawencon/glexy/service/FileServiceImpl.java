package com.lawencon.glexy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.glexy.dao.FileDao;
import com.lawencon.glexy.model.File;

@Service
public class FileServiceImpl extends BaseServiceImpl implements FileService {
	
	@Autowired
	private FileDao fileDao;
	
	@Override
	public List<File> findAll() throws Exception {
		
		return fileDao.findAll();
	}

	@Override
	public File findById(String id) throws Exception {
		
		return fileDao.findById(id);
	}

	@Override
	public File saveOrUpdate(File data) throws Exception {
	
		try {
			if (data.getId() != null) {
				File file = findById(data.getId());
				data.setFiles(file.getFiles()); 
				data.setExtension(file.getExtension()); 
				data.setCreatedAt(file.getCreatedAt());
				data.setCreatedBy(file.getCreatedBy());
				data.setVersion(file.getVersion());
				data.setIsActive(file.getIsActive());
			}

			begin();
			data = fileDao.saveOrUpdate(data);
			commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
		}
		return data;
	}

	@Override
	public boolean deleteById(String id) throws Exception {
		boolean result = false;
		try {
			begin();
			result = fileDao.deleteById(id);
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
		}
		return result;
	}

}
