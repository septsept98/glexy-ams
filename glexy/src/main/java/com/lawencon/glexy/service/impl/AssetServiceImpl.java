package com.lawencon.glexy.service.impl;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.glexy.dao.AssetDao;
import com.lawencon.glexy.dto.InsertResDto;
import com.lawencon.glexy.model.Asset;
import com.lawencon.glexy.model.Brand;
import com.lawencon.glexy.model.Company;
import com.lawencon.glexy.model.File;
import com.lawencon.glexy.model.Inventory;
import com.lawencon.glexy.model.Invoice;
import com.lawencon.glexy.model.StatusAsset;
import com.lawencon.glexy.model.TrackAsset;
import com.lawencon.glexy.service.AssetService;
import com.lawencon.glexy.service.BrandService;
import com.lawencon.glexy.service.CompanyService;
import com.lawencon.glexy.service.FileService;
import com.lawencon.glexy.service.InventoryService;
import com.lawencon.glexy.service.InvoiceService;
import com.lawencon.glexy.service.StatusAssetService;
import com.lawencon.glexy.service.TrackAssetService;
import com.lawencon.util.ExcelUtil;

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
	@Autowired
	private ExcelUtil excelUtil;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private BrandService brandService;
	@Autowired
	private StatusAssetService statusAssetService;

	private String type = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

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

			int stock = 0;
			int stockInven = inven.getStock();

			Inventory inventory = inventoryService.findByCode(inven.getCode());
			if (inventory != null) {
				stock = inventory.getStock() + stockInven;
				int latest = inventory.getLatestStock() + inven.getStock();
				inven.setId(inventory.getId());
				inven.setNameAsset(inventory.getNameAsset());
				inven.setCode(inventory.getCode());
				inven.setStock(stock);
				inven.setLatestStock(latest);
				inven.setCreatedBy(inventory.getCreatedBy());
				inven.setCreatedAt(inventory.getCreatedAt());
				inven.setUpdatedBy("4");
				inven.setVersion(inventory.getVersion());
				inven.setIsActive(inventory.getIsActive());
			} else {
				inven.setLatestStock(stockInven);
				stock = stockInven;
				inven.setCreatedBy("2");
				inven.setIsActive(true);
			}
			int invenStock = stock;
			inven = inventoryService.saveOrUpdate(inven);

			imgAsset.setCreatedBy("3");
			imgAsset.setIsActive(true);

			imgAsset.setFiles(assetImg.getBytes());
			String extAs = assetImg.getOriginalFilename();
			extAs = extAs.substring(extAs.lastIndexOf(".") + 1, extAs.length());
			imgAsset.setExtension(extAs);

			imgAsset = fileService.saveOrUpdate(imgAsset);

			asset.setAssetImg(imgAsset);
			Brand brand = brandService.findByCode(asset.getBrandId().getCode());
			Company company = companyService.findByCode(asset.getCompanyId().getCode());
			StatusAsset statusAsset = statusAssetService.findByCode(asset.getStatusAssetId().getCodeStatusAsset());

			for (int i = 0; i < stockInven; i++) {
				Asset assetInsert = new Asset();
				String codeAsset = generateCode(inven.getCode(), company.getCode(),invenStock, i);
				assetInsert.setAssetTypeId(asset.getAssetTypeId());
				
				assetInsert.setBrandId(brand);
				assetInsert.setCompanyId(company);
				assetInsert.setExpiredDate(asset.getExpiredDate());
				assetInsert.setStatusAssetId(statusAsset);
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

	@Override
	public String generateCode(String invenCode, String codeCompany, int stock, int index) throws Exception {
		return invenCode + codeCompany + (stock + index + 1);
	}

	@Override
	public List<Asset> findAllDeployAsset() throws Exception {
		return assetDao.findAllDeployAsset();
	}

	@Override
	public List<Asset> findAllGeneralAsset() throws Exception {
		return assetDao.findAllGeneralAsset();
	}

	public boolean hasExcelFormat(MultipartFile file) throws Exception {
		if (!type.equals(file.getContentType())) {
			return false;
		}

		return true;
	}

	@Override
	public InsertResDto saveExcel(MultipartFile file) throws Exception {
		try {
			Invoice invoice = new Invoice();
			int stock = 0;
			excelUtil.init("Assets", file.getInputStream());
			for (int i = 1; i < excelUtil.getRowCountInSheet(); i++) {
				Inventory inventory = inventoryService.findByCode(excelUtil.getCellData(i, 2));
				if(inventory == null) {
					inventory.setNameAsset(excelUtil.getCellData(i, 0));
					inventory.setStock(Integer.valueOf(excelUtil.getCellData(i, 1)));
					stock = inventory.getStock();
					inventory.setLatestStock(stock);
					inventory.setCode(excelUtil.getCellData(i, 2));
				}
			}
			return null;
		} catch (Exception e) {
			throw new RuntimeException("fail to store excel data: " + e.getMessage());
		}

	}

	@Override
	public String generateCode(String invenCode, int stock, int index) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
