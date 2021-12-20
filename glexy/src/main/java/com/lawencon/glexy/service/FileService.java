package com.lawencon.glexy.service;

import java.util.List;

import com.lawencon.glexy.model.File;

public interface FileService {
	
	List<File> findAll() throws Exception; 

	File findById(String id) throws Exception; 
	
	File saveOrUpdate(File data) throws Exception;
	
	boolean deleteById(String id) throws Exception;
	
	File findByByte(byte[] img, String ext) throws Exception;

}
