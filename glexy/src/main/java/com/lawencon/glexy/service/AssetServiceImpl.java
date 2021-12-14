package com.lawencon.glexy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.glexy.dao.AssetDao;
import com.lawencon.glexy.dao.InventoryDao;
import com.lawencon.glexy.dto.asset.InsertReqDataAsset;
import com.lawencon.glexy.model.Asset;
import com.lawencon.glexy.model.AssetType;
import com.lawencon.glexy.model.Company;
import com.lawencon.glexy.model.File;
import com.lawencon.glexy.model.Inventory;
import com.lawencon.glexy.model.Invoice;
import com.lawencon.glexy.model.StatusAsset;

@Service
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
	public Asset saveOrUpdate(InsertReqDataAsset data) throws Exception {
		try {
			List<Asset> asset = data.getAsset();
			Invoice inv = data.getInvoice();
			File imgInvoice = data.getImgInvoice();
			File imgAsset = data.getImgAsset();
			Inventory inven = data.getInventory();
			inven.setCreatedBy("2");
//			if(data.getId() != null) {
//				data.setCreatedAt(asset.getCreatedAt());
//				data.setCreatedBy(asset.getCreatedBy());
//				data.setVersion(asset.getVersion());
//			}
			List<Inventory> inventoryList = inventoryService.findAll();
			int index = 0;
			boolean same = false;
			
			for(int i = 0; i < inventoryList.size(); i++) {
				if(inventoryList.get(i).getNameAsset() == inven.getNameAsset()) {
					index = i;
					same = true;
				}
			}
			Inventory inventory = new Inventory();
			if(same) {
				inventory = inventoryList.get(index);
				int stok = inventory.getStock() + inven.getStock();
				int latest = inventory.getLatestStock() + inven.getStock();
				inven.setId(inventory.getId());
				inven.setStock(stok);
				inven.setLatestStock(latest);
				inven.setCreatedBy(inventory.getCreatedBy());
				inven.setCreatedAt(inventory.getCreatedAt());
				inven.setUpdatedBy("4");
				inven.setVersion(inventory.getVersion());
				inven.setIsActive(inventory.getIsActive());
			}
			begin();
			inven = inventoryService.saveOrUpdate(inven);
			commit();
			
			imgInvoice.setCreatedBy("3");
			imgInvoice.setIsActive(true);
			begin();
			
			commit();
			
			
			StatusAsset statusAsset = statusAssetService.findById(data.getStatusAssetId().getId());
			Invoice invoice = invoiceService.findById(data.getInvoiceId().getId());
			Company company = companyService.findById(data.getCompanyId().getId());
//			Inventory inventory = inventoryService.findById(data.getInventoryId().getId());
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
		boolean result = false;
		try {
			begin();
			result = assetDao.removeById(id);
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
		}
		return result;
	}
	
	
	
	

}
