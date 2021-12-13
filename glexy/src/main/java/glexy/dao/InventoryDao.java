package glexy.dao;

import java.util.List;

import glexy.model.Inventory;

public interface InventoryDao {
	
	void insert(Inventory data) throws Exception;

	void update(Inventory data) throws Exception;

	Inventory findById(String id) throws Exception;
	
	List<Inventory> findAll() throws Exception;
	
}
