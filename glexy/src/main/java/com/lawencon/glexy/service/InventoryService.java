package com.lawencon.glexy.service;

import java.util.List;

import com.lawencon.glexy.model.Employee;
import com.lawencon.glexy.model.Inventory;

public interface InventoryService {
	
	Inventory saveOrUpdate(Inventory data) throws Exception;
	
	Inventory findById(String id) throws Exception;
	
	List<Inventory> findAll() throws Exception;
	
	List<Inventory> findByName(String name) throws Exception;
	
	Inventory findByCode(String code) throws Exception;

	boolean removeById(String id) throws Exception;
	
	String generateCode(int index) throws Exception;
	
	void validationFk(String id) throws Exception;
	
	void validationSave(Inventory data) throws Exception;

	void validationUpdate(Inventory data) throws Exception;
	
}
