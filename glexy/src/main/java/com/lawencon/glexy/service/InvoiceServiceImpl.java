package com.lawencon.glexy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.glexy.dao.InvoiceDao;
import com.lawencon.glexy.model.Invoice;

@Service
public class InvoiceServiceImpl extends BaseServiceImpl implements InvoiceService {

	@Autowired
	private InvoiceDao invoiceDao;
	
	@Override
	public Invoice saveOrUpdate(Invoice data) throws Exception {
		
		try {
			if(data.getId() != null) {
				Invoice invoice = findById(data.getId());
				data.setCreatedAt(invoice.getCreatedAt());
				data.setCreatedBy(invoice.getCreatedBy());
				data.setVersion(invoice.getVersion());
			}
			
			begin();
			data = invoiceDao.saveOrUpdate(data);
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
		}
		return data;
		
	}

	@Override
	public Invoice findById(String id) throws Exception {
		return invoiceDao.findById(id);
	}

	@Override
	public List<Invoice> findAll() throws Exception {
		return invoiceDao.findAll();
	}

	@Override
	public boolean removeById(String id) throws Exception {
		boolean result = false;
		try {
			begin();
			result = invoiceDao.removeById(id);
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
		}
		return result;
	}
	
	

}
