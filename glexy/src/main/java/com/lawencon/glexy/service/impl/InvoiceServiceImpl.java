package com.lawencon.glexy.service.impl;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lawencon.glexy.dao.AssetDao;
import com.lawencon.glexy.dao.InvoiceDao;
import com.lawencon.glexy.exception.ValidationGlexyException;
import com.lawencon.glexy.model.Asset;
import com.lawencon.glexy.model.File;
import com.lawencon.glexy.model.Invoice;
import com.lawencon.glexy.service.FileService;
import com.lawencon.glexy.service.InvoiceService;

@Service
public class InvoiceServiceImpl extends BaseGlexyServiceImpl implements InvoiceService {

	@Autowired
	private InvoiceDao invoiceDao;
	@Autowired
	private FileService fileService;
	@Autowired 
	private AssetDao assetDao;
	
	@Override
	public Invoice save(Invoice data) throws Exception {

		try {
			if (data.getId() != null) {
				validationUpdate(data);
				Invoice invoice = findById(data.getId());
				data.setCreatedAt(invoice.getCreatedAt());
				data.setCreatedBy(invoice.getCreatedBy());
				data.setVersion(invoice.getVersion());
			} else {

				data.setCreatedBy("3");		
				data.setIsActive(true);

				validationSave(data);

			}
			data = invoiceDao.saveOrUpdate(data);
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
		}
		return data;

	}
	
	@Override
	public Invoice update(Invoice data, MultipartFile file) throws Exception {
		Invoice invoice = invoiceDao.findByCode(data.getCode());
		File imgInvoice = new File();
		
		imgInvoice.setFiles(file.getBytes());
		String ext = file.getOriginalFilename();
		ext = ext.substring(ext.lastIndexOf(".") + 1, ext.length());
		imgInvoice.setExtension(ext);

		File imgInsert = fileService.findByByte(imgInvoice.getFile(), ext);
		
		if(imgInsert != null) {
			invoice.setInvoiceImg(imgInsert);
		} else {
			invoice.setInvoiceImg(imgInvoice);
		}
		
		invoice.setUpdatedBy(getIdAuth());
		
		begin();
		invoice = invoiceDao.saveOrUpdate(invoice);
		commit();
		
		return invoice;
	}



	@Override
	public Invoice findById(String id) throws Exception {
		Invoice result = new Invoice();
		try {
			result = invoiceDao.findById(id);
		} catch (NoResultException e) {
			e.printStackTrace();
			throw new NoResultException("Invoice not found");
		}
		return result;
	}

	@Override
	public List<Invoice> findAll() throws Exception {
		return invoiceDao.findAll();
	}

	@Override
	public boolean removeById(String id) throws Exception {
		boolean result = false;
		try {
			validationFk(id);
			begin();
			result = invoiceDao.removeById(id);
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
		}
		return result;
	}

	@Override
	public void validationFk(String id) throws Exception {

		List<Asset> dataAsset = assetDao.findByInventoryId(id);
		if (dataAsset != null) {

			throw new ValidationGlexyException("Invoice in Use");
		}

	}

	@Override
	public void validationSave(Invoice data) throws Exception {
		if (data.getCode() == null || data.getTotalPrice() == null) {
			throw new ValidationGlexyException("Data not Complete");
		}

	}

	@Override
	public void validationUpdate(Invoice data) throws Exception {
		if (data.getId() != null) {
			Invoice invoice = findById(data.getId());
			if (invoice == null) {
				throw new ValidationGlexyException("Data not Found");
			}
		} else {
			throw new ValidationGlexyException("Data not Found");
		}
		if (data.getCode() == null || data.getPurchaseDate() == null || data.getTotalPrice() == null) {
			throw new ValidationGlexyException("Data not Complete");
		}

	}

}
