package com.lawencon.glexy.dao;

import java.util.List;

import com.lawencon.glexy.model.Inventory;

public interface InventoryDao {
	
	void insert(Inventory data) throws Exception;

	void update(Inventory data) throws Exception;

	Inventory findById(String id) throws Exception;
	
	List<Inventory> findAll() throws Exception;
	
}
