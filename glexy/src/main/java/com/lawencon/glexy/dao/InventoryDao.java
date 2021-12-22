package com.lawencon.glexy.dao;

import java.util.List;

import com.lawencon.glexy.model.Inventory;

public interface InventoryDao {
	
	Inventory saveOrUpdate(Inventory data) throws Exception;

	Inventory findById(String id) throws Exception;
	
	List<Inventory> findByName(String name) throws Exception;
	
	Inventory findByCode(String code) throws Exception;
	
	List<Inventory> findAll() throws Exception;
	
	boolean removeById(String id) throws Exception;
	
	
	
}
