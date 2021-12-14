package com.lawencon.glexy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.glexy.dao.AssetDao;
import com.lawencon.glexy.model.Asset;
import com.lawencon.glexy.model.AssetType;
import com.lawencon.glexy.model.Company;
import com.lawencon.glexy.model.Inventory;
import com.lawencon.glexy.model.Invoice;
import com.lawencon.glexy.model.StatusAsset;

public class AssetServiceImpl extends BaseServiceImpl implements AssetService {
	
	@Autowired
	private AssetDao assetDao;
	@Autowired
	private StatusAssetService statusAssetService;
	@Autowired
	private InvoiceService invoiceService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private AssetTypeService assetTypeService;
	@Autowired
	private InventoryService inventoryService;
	
	
	@Override
	public Asset saveOrUpdate(Asset data) throws Exception {
		try {
			if(data.getId() != null) {
				Asset asset = findById(data.getId());
				data.setCreatedAt(asset.getCreatedAt());
				data.setCreatedBy(asset.getCreatedBy());
				data.setVersion(asset.getVersion());
			}
			
			StatusAsset statusAsset = statusAssetService.findById(data.getStatusAssetId().getId());
			Invoice invoice = invoiceService.findById(data.getInvoiceId().getId());
			Company company = companyService.findById(data.getCompanyId().getId());
			Inventory inventory = inventoryService.findById(data.getInventoryId().getId());
			AssetType assetType = assetTypeService.findById(data.getAssetTypeId().getId());
			
			if(statusAsset != null && invoice != null && company != null && inventory != null && assetType != null) {
				data.setStatusAssetId(statusAsset);
				data.setInvoiceId(invoice);
				data.setCompanyId(company);
				data.setInventoryId(inventory);
				data.setAssetTypeId(assetType);

				begin();
				data = assetDao.saveOrUpdate(data);
				commit();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
		}
		return data;
	}

	@Override
	public Asset findById(String id) throws Exception {
		return assetDao.findById(id);
	}

	@Override
	public List<Asset> findAll() throws Exception {
		return assetDao.findAll();
	}

	@Override
	public boolean removeById(String id) throws Exception {
		return assetDao.removeById(id);
	}
	
	
	
	

}
