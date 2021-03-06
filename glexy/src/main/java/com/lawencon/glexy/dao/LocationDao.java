package com.lawencon.glexy.dao;

import java.util.List;

import com.lawencon.glexy.model.Location;

public interface LocationDao {
	
	Location saveOrUpdate(Location data) throws Exception;

	Location findById(String id) throws Exception;
	
	List<Location> findAll() throws Exception;
	
	List<Location> search(String search) throws Exception;
	
	boolean removeById(String id) throws Exception;
	
	List<Location> findByCompanyId(String id) throws Exception;
}
