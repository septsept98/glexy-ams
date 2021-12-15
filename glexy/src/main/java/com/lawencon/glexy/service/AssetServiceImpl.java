package com.lawencon.glexy.service;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.glexy.dao.AssetDao;
import com.lawencon.glexy.dto.asset.InsertReqDataAsset;
import com.lawencon.glexy.model.Asset;
import com.lawencon.glexy.model.File;
import com.lawencon.glexy.model.Inventory;
import com.lawencon.glexy.model.Invoice;
import com.lawencon.glexy.model.StatusAsset;
import com.lawencon.glexy.model.TrackAsset;

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
	@Autowired
	private TrackAssetService trackAssetService;
	
	
	@Override
	public Asset save(InsertReqDataAsset data, MultipartFile invoiceImg, MultipartFile assetImg) throws Exception {
		Asset asset = new Asset();
		try {
			asset = data.getAsset();
			Invoice invoice = data.getInvoice();
			File imgInvoice = new File();
			File imgAsset = new File();
			Inventory inven = data.getInventory();
			
			
			invoice.setCreatedBy("3");
			invoice.setPurchaseDate(LocalDate.now());
			if(invoiceImg == null) {
				Invoice invo = invoiceService.findById(invoice.getId());
				invoice.setInvoiceImg(invo.getInvoiceImg());
			} else {
				imgInvoice.setFile(invoiceImg.getBytes());
				String ext = invoiceImg.getOriginalFilename();
				ext = ext.substring(ext.lastIndexOf(".")+1, ext.length());
				imgInvoice.setExtension(ext);
				
				begin();
				imgInvoice = fileService.saveOrUpdate(imgInvoice);
				commit();
				
				invoice.setInvoiceImg(imgInvoice);
			}
			
			begin();
			invoice = invoiceService.saveOrUpdate(invoice);
			commit();
				
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
			
			imgAsset.setCreatedBy("3");
			imgAsset.setIsActive(true);
			
			if(assetImg == null) {
				Asset ass = assetDao.findById(asset.getId());
				asset.setAssetImg(ass.getAssetImg());
			} else {
				imgAsset.setFile(assetImg.getBytes());
				String ext = assetImg.getOriginalFilename();
				ext = ext.substring(ext.lastIndexOf(".")+1, ext.length());
				imgAsset.setExtension(ext);
				
				begin();
				imgAsset = fileService.saveOrUpdate(imgAsset);
				commit();
				
				asset.setAssetImg(imgAsset);
			}
			
			
			for(int i = 0; i < stockInven; i++) {
				String codeAsset = generateCode(inven.getCode(), invenStock, i);
				asset.setNames(inven.getNameAsset() + (invenStock+i+1));
				asset.setCode(codeAsset);
				asset.setCreatedBy("4");
				asset.setInvoiceId(invoice);
				asset.setInventoryId(inven);
	
				begin();
				assetDao.saveOrUpdate(asset);
				commit();
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new Exception(e);
		}
		return asset;
	}
	
	
	@Override
	public Asset update(Asset data) throws Exception {
		Asset asset = findById(data.getId());
		StatusAsset statusAsset = new StatusAsset();
		statusAsset.setId(data.getStatusAssetId().getId());
		asset.setStatusAssetId(statusAsset);
		asset.setUpdatedBy("1");
		
		begin();
		asset = assetDao.saveOrUpdate(asset);
		commit();
		
		TrackAsset trackAsset = new TrackAsset();
		trackAsset.setCodeAsset(asset.getCode());
		trackAsset.setNameActivity("Update Status Asset");
		trackAsset.setDateActivity(LocalDate.now());
		trackAsset.setUserId("2");
		trackAsset.setTransactionCode("BBA");
		
		begin();
		trackAssetService.saveOrUpdate(trackAsset);
		commit();
		
		
		return asset;
	}


	@Override
	public Asset findById(String id) throws Exception {
		Asset result = new Asset();
		try {
			result = assetDao.findById(id);
		} catch (NoResultException e) {
			e.printStackTrace();
			throw new NoResultException("Asset not found");
		}
		return result;
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
  
	public String generateCode(String invenCode,int stock, int index) throws Exception {
		return invenCode + "" + (stock+index+1);
	}
}
