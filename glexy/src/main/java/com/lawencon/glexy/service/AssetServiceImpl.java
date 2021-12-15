package com.lawencon.glexy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.glexy.dao.AssetDao;
import com.lawencon.glexy.dto.asset.InsertReqDataAsset;
import com.lawencon.glexy.model.Asset;
import com.lawencon.glexy.model.File;
import com.lawencon.glexy.model.Inventory;
import com.lawencon.glexy.model.Invoice;

@Service
public class AssetServiceImpl extends BaseServiceImpl implements AssetService {
	
	@Autowired
	private AssetDao assetDao;
	@Autowired
	private InvoiceService invoiceService;
	@Autowired
	private InventoryService inventoryService;
	@Autowired
	private FileService fileService;
	
	
	@Override
	public Asset saveOrUpdate(InsertReqDataAsset data) throws Exception {
		Asset asset = new Asset();
		try {
			asset = data.getAsset();
			Invoice invoice = data.getInvoice();
			File imgInvoice = data.getImgInvoice();
			File imgAsset = data.getImgAsset();
			Inventory inven = data.getInventory();
			int invenStock = 0;
			inven.setCreatedBy("2");
			int stockInven = inven.getStock();
			
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
				invenStock = stok;
			}
			begin();
			inven = inventoryService.saveOrUpdate(inven);
			commit();
			
			imgInvoice.setCreatedBy("3");
			imgInvoice.setIsActive(true);
			begin();
			imgInvoice = fileService.saveOrUpdate(imgInvoice);
			commit();
			
			imgAsset.setCreatedBy("3");
			imgAsset.setIsActive(true);
			begin();
			imgAsset = fileService.saveOrUpdate(imgAsset);
			commit();
			
			invoice.setInvoiceImg(imgInvoice);
			invoice.setCreatedBy("3");
			invoice.setIsActive(true);
			
			begin();
			invoice = invoiceService.saveOrUpdate(invoice);
			commit();
			
			for(int i = 0; i < stockInven; i++) {
				String codeAsset = inven.getCode() + (invenStock+i+1);
				asset.setCode(codeAsset);
				asset.setCreatedBy("4");
				asset.setAssetImg(imgAsset);
				asset.setInvoiceId(invoice);
				asset.setInventoryId(inven);
				
				begin();
				assetDao.saveOrUpdate(asset);
				commit();
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
		}
		return asset;
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

	@Override
	public List<Asset> findByInvent(String idInvent) throws Exception {
		return assetDao.findByInvent(idInvent);
	}

}
