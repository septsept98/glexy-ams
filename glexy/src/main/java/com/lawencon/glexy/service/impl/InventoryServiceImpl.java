package com.lawencon.glexy.service.impl;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.glexy.dao.InventoryDao;
import com.lawencon.glexy.model.Inventory;
import com.lawencon.glexy.service.InventoryService;

@Service
public class InventoryServiceImpl extends BaseServiceImpl implements InventoryService{

	@Autowired
	private InventoryDao inventoryDao;
	
	@Override
	public Inventory saveOrUpdate(Inventory data) throws Exception {
		try {
			if(data.getId() != null) {
				Inventory inventory = findById(data.getId());
				data.setNameAsset(inventory.getNameAsset());
				data.setCode(inventory.getCode());
				data.setStock(inventory.getStock());
				data.setCreatedAt(inventory.getCreatedAt());
				data.setCreatedBy(inventory.getCreatedBy());
				data.setUpdatedBy("1");
				data.setVersion(inventory.getVersion());
				data = inventoryDao.saveOrUpdate(data);
			} else {
				
				data.setCreatedBy("3");
				data = inventoryDao.saveOrUpdate(data);
			}
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
		}
		return data;
	}

	@Override
	public Inventory findById(String id) throws Exception {
		Inventory result = new Inventory();
		try {
			result = inventoryDao.findById(id);
		} catch (NoResultException e) {
			e.printStackTrace();
			throw new NoResultException("Inventory not found");
		}
		return result;
	}
	

	@Override
	public List<Inventory> findAll() throws Exception {
		return inventoryDao.findAll();
	}

	@Override
	public List<Inventory> findByName(String name) throws Exception {
		return inventoryDao.findByName(name);
	}
	
	@Override
	public Inventory findByCode(String code) throws Exception {
		return inventoryDao.findByCode(code);
	}

	@Override
	public boolean removeById(String id) throws Exception {
		boolean result = false;
		try {
			begin();
			result = inventoryDao.removeById(id);
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
		}
		return result;
	}

	@Override
	public String generateCode(int index) throws Exception {
		return "inven" + "" + (index+1);
	}
	
	
	
	

}
