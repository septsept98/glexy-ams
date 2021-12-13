package com.lawencon.glexy.dao;

import java.util.List;

import com.lawencon.glexy.model.Location;

public interface LocationDao {
	
	void insert(Location data) throws Exception;

	void update(Location data) throws Exception;

	Location findById(String id) throws Exception;
	
	List<Location> findAll() throws Exception;
	

}
