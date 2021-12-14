package com.lawencon.glexy.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.BaseDaoImpl;
import com.lawencon.glexy.model.Invoice;

@Repository
public class InvoiceDaoImpl extends BaseDaoImpl<Invoice> implements InvoiceDao {

	@Override
	public Invoice saveOrUpdate(Invoice data) throws Exception {
		return save(data);
	}

	@Override
	public boolean removeById(String id) throws Exception {
		return deleteById(id);
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
