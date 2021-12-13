package glexy.dao;

import java.util.List;

import com.lawencon.base.BaseDaoImpl;

import glexy.model.Invoice;

public class InvoiceDaoImpl extends BaseDaoImpl<Invoice> implements InvoiceDao {

	@Override
	public void insert(Invoice data) throws Exception {
		save(data);
	}

	@Override
	public void update(Invoice data) throws Exception {
		save(data);		
	}

	@Override
	public Invoice findById(String id) throws Exception {
		return getById(id);
	}

	@Override
	public List<Invoice> findAll() throws Exception {
		return getAll();
	}
	

}
