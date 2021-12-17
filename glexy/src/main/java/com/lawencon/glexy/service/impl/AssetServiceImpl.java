package com.lawencon.glexy.service.impl;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.glexy.dao.AssetDao;
import com.lawencon.glexy.model.Asset;
import com.lawencon.glexy.model.File;
import com.lawencon.glexy.model.Inventory;
import com.lawencon.glexy.model.Invoice;
import com.lawencon.glexy.model.StatusAsset;
import com.lawencon.glexy.model.TrackAsset;
import com.lawencon.glexy.service.AssetService;
import com.lawencon.glexy.service.FileService;
import com.lawencon.glexy.service.InventoryService;
import com.lawencon.glexy.service.InvoiceService;
import com.lawencon.glexy.service.TrackAssetService;

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
	public Asset save(Asset data, MultipartFile invoiceImg, MultipartFile assetImg) throws Exception {
		try {
			Asset asset = data;
			Invoice invoice = data.getInvoiceId();
			Inventory inven = data.getInventoryId();
			File imgInvoice = new File();
			File imgAsset = new File();
			
			imgInvoice.setFiles(invoiceImg.getBytes());
			String ext = invoiceImg.getOriginalFilename();
			ext = ext.substring(ext.lastIndexOf(".") + 1, ext.length());
			imgInvoice.setExtension(ext);

			begin();
			
			imgInvoice = fileService.saveOrUpdate(imgInvoice);

			invoice.setInvoiceImg(imgInvoice);
			invoice.setCreatedBy("3");
			invoice.setPurchaseDate(LocalDate.now());
			invoice = invoiceService.saveOrUpdate(invoice);

			int invenStock = 0;
			inven.setCreatedBy("2");
			int stockInven = inven.getStock();

			List<Inventory> inventoryList = inventoryService.findAll();
			int index = 0;
			boolean same = false;
			boolean duplicat = false;

			for (int i = 0; i < inventoryList.size(); i++) {
				if (inventoryList.get(i).getNameAsset() == inven.getNameAsset()) {
					index = i;
					same = true;
				}
			}
			for (int i = 0; i < inventoryList.size(); i++) {
				if (inventoryList.get(i).getCode() == inven.getCode()) {
					duplicat = true;
				}
			}
			Inventory inventory = new Inventory();
			if (same) {
				if(duplicat) {
					inventory = inventoryService.findByCode(inven.getCode());
					inven.setId(inventory.getId());
				}
				inventory = inventoryList.get(index);
				int stok = inventory.getStock() + inven.getStock();
				int latest = inventory.getLatestStock() + inven.getStock();
				inven.setNameAsset(inventory.getNameAsset());
				inven.setCode(inventory.getCode());
				inven.setStock(stok);
				inven.setLatestStock(latest);
				inven.setCreatedBy(inventory.getCreatedBy());
				inven.setCreatedAt(inventory.getCreatedAt());
				inven.setUpdatedBy("4");
				inven.setVersion(inventory.getVersion());
				inven.setIsActive(inventory.getIsActive());
				invenStock = stok;
				inven = inventoryService.saveOrUpdate(inven);
			}


			imgAsset.setCreatedBy("3");
			imgAsset.setIsActive(true);
			
			imgAsset.setFiles(assetImg.getBytes());
			String extAs = assetImg.getOriginalFilename();
			extAs = extAs.substring(extAs.lastIndexOf(".") + 1, extAs.length());
			imgAsset.setExtension(extAs);

			imgAsset = fileService.saveOrUpdate(imgAsset);

			asset.setAssetImg(imgAsset);

			for (int i = 0; i < stockInven; i++) {
				Asset assetInsert = new Asset(); 
				String codeAsset = generateCode(inven.getCode(), invenStock, i);
				assetInsert.setAssetTypeId(asset.getAssetTypeId());
				assetInsert.setBrandId(asset.getBrandId());
				assetInsert.setCompanyId(asset.getCompanyId());
				assetInsert.setExpiredDate(asset.getExpiredDate());
				assetInsert.setStatusAssetId(asset.getStatusAssetId());
				assetInsert.setStatusAssetId(asset.getStatusAssetId());
				assetInsert.setNames(inven.getNameAsset());
				assetInsert.setCode(codeAsset);
				assetInsert.setCreatedBy("4");
				assetInsert.setInvoiceId(invoice);
				assetInsert.setInventoryId(inven);
				
				assetDao.saveOrUpdate(assetInsert);

				TrackAsset trackAsset = new TrackAsset();
				trackAsset.setCodeAsset(assetInsert.getCode());
				trackAsset.setNameActivity("New");
				trackAsset.setDateActivity(LocalDate.now());
				trackAsset.setUserId("2");
				trackAsset.setTransactionCode("BBA");
				trackAsset.setCreatedBy("2");
				trackAsset.setIsActive(true);
				
				trackAssetService.saveOrUpdate(trackAsset);
			}
			
			
			commit();

			return asset;
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new Exception(e);
		}
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

		TrackAsset trackAsset = new TrackAsset();
		trackAsset.setCodeAsset(asset.getCode());
		trackAsset.setNameActivity("Update Status Asset");
		trackAsset.setDateActivity(LocalDate.now());
		trackAsset.setUserId("2");
		trackAsset.setTransactionCode("BBA");
		trackAsset.setCreatedBy("2");
		trackAsset.setIsActive(true);

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

	@Override
	public List<Asset> findByBrandId(String idBrand) throws Exception {
		return assetDao.findByBrandId(idBrand);
	}

	@Override
	public List<Asset> findByCompanyId(String idCompany) throws Exception {
		return assetDao.findByCompanyId(idCompany);
	}

	public String generateCode(String invenCode, int stock, int index) throws Exception {
		return invenCode + "" + (stock + index + 1);
	}

	@Override
	public List<Asset> findAllDeployAsset() throws Exception {
		return assetDao.findAllDeployAsset();
	}

	@Override
	public List<Asset> findAllGeneralAsset() throws Exception {
		return assetDao.findAllGeneralAsset();
	}
}
