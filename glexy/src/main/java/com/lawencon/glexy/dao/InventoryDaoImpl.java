package com.lawencon.glexy.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.BaseDaoImpl;
import com.lawencon.glexy.model.Inventory;

@Repository
public class InventoryDaoImpl extends BaseDaoImpl<Inventory> implements InventoryDao {

	@Override
	public Inventory saveOrUpdate(Inventory data) throws Exception {
		return save(data);
	}

	@Override
	public boolean deleteById(String id) throws Exception {
		return super.deleteById(id);
	}

	@Override
	public Inventory findById(String id) throws Exception {
		return getById(id);
	}

	@Override
	public List<Inventory> findAll() throws Exception {
		return getAll();
	}
	
	

}