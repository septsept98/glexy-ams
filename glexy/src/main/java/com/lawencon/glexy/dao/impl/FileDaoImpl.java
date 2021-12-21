package com.lawencon.glexy.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import org.springframework.stereotype.Repository;

import com.lawencon.base.BaseDaoImpl;
import com.lawencon.glexy.dao.FileDao;
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

	@Override
	public File findByByte(byte[] img, String ext) throws Exception {
		File file = new File();
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("Select id ");
			sql.append("FROM files ");
			sql.append("WHERE file = :file AND extension = :extension");
			
			Object result = createNativeQuery(sql.toString())
					.setParameter("file", img)
					.setParameter("extension", ext)
					.getSingleResult();	
			if(result != null) {
				file.setId(result.toString());
				file = getById(file.getId());
			}

		}  catch (NoResultException e) {
			return null;
		} catch (NonUniqueResultException e) {
			e.printStackTrace();
		}
		return file;
	}
	
	

	
}
