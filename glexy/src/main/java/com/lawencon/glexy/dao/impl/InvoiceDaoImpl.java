package com.lawencon.glexy.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import org.springframework.stereotype.Repository;

import com.lawencon.base.BaseDaoImpl;
import com.lawencon.glexy.dao.InvoiceDao;
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

	@Override
	public Invoice findByCode(String code) throws Exception {
		Invoice invoice = new Invoice();
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("Select id ");
			sql.append("FROM invoices ");
			sql.append("WHERE code=:code");
			
			Object result = createNativeQuery(sql.toString())
					.setParameter("code", code)
					.getSingleResult();	
			if(result != null) {
				invoice.setId(result.toString());
				invoice = getById(invoice.getId());
			}

		}  catch (NoResultException e) {
			e.printStackTrace();
		} catch (NonUniqueResultException e) {
			e.printStackTrace();
		}
		return invoice;
	}
	
	

}
