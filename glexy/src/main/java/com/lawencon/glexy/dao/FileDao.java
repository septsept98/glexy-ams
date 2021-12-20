package com.lawencon.glexy.dao;

import java.util.List;

import com.lawencon.glexy.model.File;

public interface FileDao {
	
	List<File> findAll() throws Exception; 

	File findById(String id) throws Exception; 
	
	File saveOrUpdate(File file) throws Exception;
	
	boolean deleteById(String id) throws Exception;
	
	File findByByte(byte[] img, String ext) throws Exception;

}
