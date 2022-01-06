package com.lawencon.glexy.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

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
	public List<Inventory> searchByNameCode(String search) throws Exception {
		List<Inventory> listResult = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("Select id ");
		sql.append("FROM inventories ");
		sql.append("WHERE lower(name_asset) LIKE lower('%" + search + "%') OR lower(code) LIKE lower('%" + search + "%') ");

		List<?> result = createNativeQuery(sql.toString()).getResultList();

		result.forEach(rs -> {
			Inventory inventory = new Inventory();
			inventory.setId(rs.toString());
			inventory = getById(inventory.getId());

			listResult.add(inventory);
		});
		return listResult;
	}

	@Override
	public Inventory findByCode(String code) throws Exception {
		Inventory inven = new Inventory();
		try {
			
			StringBuilder sql = new StringBuilder();
			sql.append("Select id ");
			sql.append("FROM inventories ");
			sql.append("WHERE code=:code");
			
			Object result = createNativeQuery(sql.toString())
					.setParameter("code", code)
					.getSingleResult();	
			if(result != null) {
				inven.setId(result.toString());
				inven = getById(inven.getId());
			}

		} catch (NoResultException e) {
			return null;
		} catch (NonUniqueResultException e) {
			e.printStackTrace();
		}
		return inven;
	}

	@Override
	public List<Inventory> searchByCodeNameComp(String search) throws Exception {
		List<Inventory> listResult = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("Select i.id ");
		sql.append("FROM inventories i ");
		sql.append("INNER JOIN assets a ON a.inventory_id = i.id ");
		sql.append("INNER JOIN asset_types at2 ON at2.id = a.asset_type_id ");
		sql.append("WHERE lower(at2.names) = lower('Component') AND ");
		sql.append("(lower(i.code) LIKE lower('%" + search + "%') OR lower(i.name_asset) LIKE lower('%" + search + "%')) ");
		sql.append("GROUP BY i.id ");

		List<?> result = createNativeQuery(sql.toString()).getResultList();
		
		result.forEach(rs -> {
			Inventory invent = new Inventory();
			invent.setId(rs.toString());
			invent = getById(invent.getId());

			listResult.add(invent);
		});

		return listResult;
	}

	@Override
	public List<Inventory> searchByCodeNameLicn(String search) throws Exception {
		List<Inventory> listResult = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("Select i.id ");
		sql.append("FROM inventories i ");
		sql.append("INNER JOIN assets a ON a.inventory_id = i.id ");
		sql.append("INNER JOIN asset_types at2 ON at2.id = a.asset_type_id ");
		sql.append("WHERE lower(at2.names) NOT IN (lower('License')) ");
		sql.append("(lower(i.code) LIKE lower('%" + search + "%') OR lower(i.name_asset) LIKE lower('%" + search + "%')) ");
		sql.append("GROUP BY i.id ");

		List<?> result = createNativeQuery(sql.toString()).getResultList();
		
		result.forEach(rs -> {
			Inventory invent = new Inventory();
			invent.setId(rs.toString());
			invent = getById(invent.getId());

			listResult.add(invent);
		});

		return listResult;
	}
}
