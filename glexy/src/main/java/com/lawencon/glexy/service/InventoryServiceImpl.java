package com.lawencon.glexy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.glexy.dao.InventoryDao;
import com.lawencon.glexy.model.Inventory;

public class InventoryServiceImpl extends BaseServiceImpl implements InventoryService{

	@Autowired
	private InventoryDao inventoryDao;
	
	@Override
	public Inventory saveOrUpdate(Inventory data) throws Exception {
		try {
			if(data.getId() != null) {
				Inventory inventory = findById(data.getId());
				data.setCreatedAt(inventory.getCreatedAt());
				data.setCreatedBy(inventory.getCreatedBy());
				data.setVersion(inventory.getVersion());
			}
			begin();
			data = inventoryDao.saveOrUpdate(data);
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
		}
		return data;
	}

	@Override
	public Inventory findById(String id) throws Exception {
		return inventoryDao.findById(id);
	}

	@Override
	public List<Inventory> findAll() throws Exception {
		return inventoryDao.findAll();
	}

	@Override
	public boolean deleteById(String id) throws Exception {
		return inventoryDao.deleteById(id);
	}
	
	

}
