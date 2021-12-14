package com.lawencon.glexy.service;

import java.util.List;

import com.lawencon.glexy.model.Inventory;

public interface InventoryService {
	
	Inventory saveOrUpdate(Inventory data) throws Exception;
	
	Inventory findById(String id) throws Exception;
	
	List<Inventory> findAll() throws Exception;
	
	boolean deleteById(String id) throws Exception;
	
}