package com.lawencon.glexy.service;

import java.util.List;

import com.lawencon.glexy.model.Location;

public interface LocationService {

	Location saveOrUpdate(Location data) throws Exception;
	
	Location findById(String id) throws Exception;
	
	List<Location> findAll() throws Exception;
	
	boolean removeById(String id) throws Exception;
}
