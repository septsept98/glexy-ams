package glexy.dao;

import java.util.List;

import glexy.model.Invoice;

public interface InvoiceDao {

	void insert(Invoice data) throws Exception;

	void update(Invoice data) throws Exception;

	Invoice findById(String id) throws Exception;
	
	List<Invoice> findAll() throws Exception;
	
	
}