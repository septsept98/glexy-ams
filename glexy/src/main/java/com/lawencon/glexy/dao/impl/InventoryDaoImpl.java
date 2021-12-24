package com.lawencon.glexy.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.BaseDaoImpl;
import com.lawencon.glexy.dao.InventoryDao;
import com.lawencon.glexy.model.Inventory;

@Repository
public class InventoryDaoImpl extends BaseDaoImpl<Inventory> implements InventoryDao {

	@Override
	public Inventory saveOrUpdate(Inventory data) throws Exception {
		return save(data);
	}

	@Override
	public boolean removeById(String id) throws Exception {
		return deleteById(id);
	}

	@Override
	public Inventory findById(String id) throws Exception {
		return getById(id);
	}

	@Override
	public List<Inventory> findAll() throws Exception {
		return getAll();
	}

	@Override
	public List<Inventory> findByNameCode(String search) throws Exception {
		List<Inventory> listResult = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("Select id ");
		sql.append("FROM inventories ");
		sql.append("WHERE name_asset LIKE '%" + search + "%' OR code LIKE '%" + search + "%' ");

		List<?> result = createNativeQuery(sql.toString()).getResultList();

		result.forEach(rs -> {
			Inventory inventory = new Inventory();
			inventory.setId(rs.toString());
			inventory = getById(inventory.getId());

			listResult.add(inventory);
		});
		return listResult;
	}
	

}
