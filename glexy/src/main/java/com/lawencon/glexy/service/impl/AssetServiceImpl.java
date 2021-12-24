package com.lawencon.glexy.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lawencon.glexy.dao.AssetDao;
import com.lawencon.glexy.dao.TransactionDetailDao;
import com.lawencon.glexy.dto.ResDto;
import com.lawencon.glexy.email.EmailHandler;
import com.lawencon.glexy.exception.ValidationGlexyException;
import com.lawencon.glexy.helper.EmailHelper;
import com.lawencon.glexy.helper.ReportDataExpiredAsset;
import com.lawencon.glexy.model.Asset;
import com.lawencon.glexy.model.AssetType;
import com.lawencon.glexy.model.Brand;
import com.lawencon.glexy.model.Company;
import com.lawencon.glexy.model.File;
import com.lawencon.glexy.model.Inventory;
import com.lawencon.glexy.model.Invoice;
import com.lawencon.glexy.model.StatusAsset;
import com.lawencon.glexy.model.TrackAsset;
import com.lawencon.glexy.model.TransactionDetail;
import com.lawencon.glexy.model.Users;
import com.lawencon.glexy.service.AssetService;
import com.lawencon.glexy.service.AssetTypeService;
import com.lawencon.glexy.service.BrandService;
import com.lawencon.glexy.service.CompanyService;
import com.lawencon.glexy.service.FileService;
import com.lawencon.glexy.service.InventoryService;
import com.lawencon.glexy.service.InvoiceService;
import com.lawencon.glexy.service.StatusAssetService;
import com.lawencon.glexy.service.TrackAssetService;
import com.lawencon.glexy.service.UsersService;
import com.lawencon.util.ExcelUtil;
import com.lawencon.util.JasperUtil;

@Service
public class AssetServiceImpl extends BaseGlexyServiceImpl implements AssetService {

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
	@Autowired
	private AssetTypeService assetTypeService;
	@Autowired
	private TransactionDetailDao transactionDetailDao;
	@Autowired
	private UsersService usersService;
	@Autowired
	private EmailHandler emailHandler;

	private String type = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

	@Override
	public Asset save(Asset data, MultipartFile invoiceImg, MultipartFile assetImg) throws Exception {
		try {
			validationSave(data);
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
			invoice.setPurchaseDate(LocalDate.now());
			invoice = invoiceService.save(invoice);

			int stock = 0;
			int stockInven = inven.getStock();
			int index = 0;

			Inventory inventory = inventoryService.findById(inven.getCode()); //code
			if (inventory != null) {
				stock = inventory.getStock() + stockInven;
				System.out.println("Stock Inventory : " + stock);
				int latest = inventory.getLatestStock() + stockInven;
				inven.setId(inventory.getId());
				inven.setNameAsset(inventory.getNameAsset());
				inven.setCode(inventory.getCode());
				inven.setStock(stock);
				inven.setLatestStock(latest);
				inven.setCreatedBy(inventory.getCreatedBy());
				inven.setCreatedAt(inventory.getCreatedAt());
				inven.setVersion(inventory.getVersion());
				inven.setIsActive(inventory.getIsActive());
				index = inventory.getStock();
			} else {
				inven.setLatestStock(stockInven);
				stock = stockInven;
				inven.setIsActive(true);
			}
			int invenStock = stock;
			inven = inventoryService.saveOrUpdate(inven);

			imgAsset.setFiles(assetImg.getBytes());
			String extAs = assetImg.getOriginalFilename();
			extAs = extAs.substring(extAs.lastIndexOf(".") + 1, extAs.length());
			imgAsset.setExtension(extAs);

			imgAsset = fileService.saveOrUpdate(imgAsset);

			asset.setAssetImg(imgAsset);

			Brand brand = brandService.findById(asset.getBrandId().getCode());
			if (brand == null) {
				throw new ValidationGlexyException("Brand Not Found");
			}
			Company company = companyService.findById(asset.getCompanyId().getCode());
			if (company == null) {
				throw new ValidationGlexyException("Company Not Found");
			}
			StatusAsset statusAsset = statusAssetService.findByCode(asset.getStatusAssetId().getCodeStatusAsset());
			if (statusAsset == null) {
				throw new ValidationGlexyException("Status Asset Not Found");
			}
			AssetType assetType = assetTypeService.findByCode(asset.getAssetTypeId().getCode());

			if (assetType == null) {
				throw new ValidationGlexyException("Asset Type Not Found");
			}

			for (int i = index; i < invenStock; i++) {
				Asset assetInsert = new Asset();
				String codeAsset = generateCode(inven.getCode(), company.getCode(), invenStock, i);
				assetInsert.setAssetTypeId(assetType);

				assetInsert.setBrandId(brand);
				assetInsert.setCompanyId(company);
				assetInsert.setExpiredDate(asset.getExpiredDate());
				assetInsert.setStatusAssetId(statusAsset);
				assetInsert.setNames(inven.getNameAsset());
				assetInsert.setAssetImg(asset.getAssetImg());
				assetInsert.setCode(codeAsset);
				assetInsert.setCreatedBy(getIdAuth());
				assetInsert.setInvoiceId(invoice);
				assetInsert.setInventoryId(inven);

				assetDao.saveOrUpdate(assetInsert);

				TrackAsset trackAsset = new TrackAsset();
				trackAsset.setCodeAsset(assetInsert.getCode());
				trackAsset.setNameActivity("New");
				trackAsset.setDateActivity(LocalDate.now());
				trackAsset.setUserId(getIdAuth());
				trackAsset.setTransactionCode("BBA");
				trackAsset.setCreatedBy(getIdAuth());
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
		if (asset == null) {
			throw new ValidationGlexyException("Asset Not Found");
		}
		StatusAsset statusAsset = new StatusAsset();

		statusAsset = statusAssetService.findById(data.getStatusAssetId().getId());
		if (statusAsset == null) {
			throw new ValidationGlexyException("Status Asset Not Found");
		}

		statusAsset.setId(data.getStatusAssetId().getId());
		asset.setStatusAssetId(statusAsset);
		asset.setUpdatedBy(getIdAuth());

		begin();
		asset = assetDao.saveOrUpdate(asset);

		TrackAsset trackAsset = new TrackAsset();
		trackAsset.setCodeAsset(asset.getCode());
		trackAsset.setNameActivity("Update Status Asset");
		trackAsset.setDateActivity(LocalDate.now());
		trackAsset.setUserId(getIdAuth());
		trackAsset.setTransactionCode("BBA");
		trackAsset.setCreatedBy(getIdAuth());
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
			validationFk(id);
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
		return invenCode + codeCompany + (index + 1);
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
	public void saveExcel(MultipartFile file) throws Exception {

		try {
			Integer stock = 0;
			int index = 0;
			excelUtil.init("Assets", file.getInputStream());
			begin();
			for (int i = 1; i < excelUtil.getRowCountInSheet(); i++) {
				Inventory inventory = new Inventory();
				inventory.setNameAsset(excelUtil.getCellData(i, 0));
				stock = Double.valueOf(excelUtil.getCellData(i, 1)).intValue();
				String codeInsert = excelUtil.getCellData(i, 2);
				inventory = inventoryService.findById(codeInsert); //bycode
				if (inventory == null) {
					Inventory inven = new Inventory();
					inven.setStock(stock);
					inven.setNameAsset(excelUtil.getCellData(i, 0));
					inven.setLatestStock(stock);
					inven.setCode(codeInsert);
					inventory = inven;
					index = 0;
				} else {
					stock = inventory.getStock() + stock;
					int latest = inventory.getLatestStock() + stock;
					inventory.setLatestStock(latest);
					index = inventory.getStock();

				}

				inventoryService.saveOrUpdate(inventory);

				Invoice invoice = new Invoice();
				invoice.setCode(excelUtil.getCellData(i, 3));
				BigDecimal bigDecimal = new BigDecimal(excelUtil.getCellData(i, 4));
				invoice.setTotalPrice(bigDecimal);
				invoice.setPurchaseDate(LocalDate.now());

				invoiceService.save(invoice);

				Brand brand = brandService.findById(excelUtil.getCellData(i, 5));
				if (brand == null) {
					throw new ValidationGlexyException("Brand Not Found");
				}

				AssetType assetType = assetTypeService.findById(excelUtil.getCellData(i, 6));
				if (assetType == null) {
					throw new ValidationGlexyException("Asset Type Not Found");
				}

				StatusAsset statusAsset = statusAssetService.findById(excelUtil.getCellData(i, 8));
				if (statusAsset == null) {
					throw new ValidationGlexyException("Status Asset Not Found");
				}

				Company company = companyService.findById(excelUtil.getCellData(i, 9));
				if (company == null) {
					throw new ValidationGlexyException("Company Not Found");
				}

				for (int j = index; j < stock; j++) {
					Asset assetInsert = new Asset();
					String codeAsset = generateCode(inventory.getCode(), company.getCode(), stock, j);
					assetInsert.setAssetTypeId(assetType);

					assetInsert.setBrandId(brand);
					assetInsert.setCompanyId(company);

					if (excelUtil.getCellData(i, 7) != null) {
						DateTimeFormatter patern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
						LocalDate date = LocalDate.parse(excelUtil.getCellData(i, 7), patern);
						assetInsert.setExpiredDate(date);
					}

					assetInsert.setStatusAssetId(statusAsset);
					assetInsert.setNames(inventory.getNameAsset());
					assetInsert.setCode(codeAsset);
					assetInsert.setCreatedBy(getIdAuth());
					assetInsert.setInvoiceId(invoice);
					assetInsert.setInventoryId(inventory);

					assetDao.saveOrUpdate(assetInsert);

					TrackAsset trackAsset = new TrackAsset();
					trackAsset.setCodeAsset(assetInsert.getCode());
					trackAsset.setNameActivity("New");
					trackAsset.setDateActivity(LocalDate.now());
					trackAsset.setUserId(getIdAuth());
					trackAsset.setTransactionCode("BBA");
					trackAsset.setCreatedBy(getIdAuth());
					trackAsset.setIsActive(true);

					trackAssetService.saveOrUpdate(trackAsset);
				}

			}
			commit();

		} catch (Exception e) {
			throw new Exception("fail to store excel data: " + e.getMessage());
		}

	}

	@Override
	public Asset updateImage(String id, MultipartFile assetImg) throws Exception {
		File imgAsset = new File();
		Asset asset = assetDao.findById(id);

		imgAsset.setFiles(assetImg.getBytes());
		String ext = assetImg.getOriginalFilename();
		ext = ext.substring(ext.lastIndexOf(".") + 1, ext.length());
		imgAsset.setExtension(ext);

		File imgInsert = fileService.findByByte(imgAsset.getFile(), ext);
		if (imgInsert != null) {
			asset.setAssetImg(imgInsert);
		} else {
			asset.setAssetImg(imgAsset);
		}
		asset.setUpdatedBy(getIdAuth());

		begin();
		asset = assetDao.saveOrUpdate(asset);
		commit();

		return asset;
	}


	@Override
	public void validationFk(String id) throws Exception {

		List<TransactionDetail> dataEmployee = transactionDetailDao.findByAssetId(id);
		if (dataEmployee != null) {

			throw new ValidationGlexyException("Asset in Use");
		}
	}

	@Override
	public List<ReportDataExpiredAsset> findExpiredAsset() throws Exception {
		List<ReportDataExpiredAsset> listResult = new ArrayList<>();
		List<Asset> listAsset = assetDao.findExpiredAsset();
		
		for(int i=0; i<listAsset.size(); i++) {
			ReportDataExpiredAsset assetDto = new ReportDataExpiredAsset();
			Asset asset = listAsset.get(i);
			assetDto.setImage(asset.getId());
			assetDto.setCodeAsset(asset.getCode());
			assetDto.setNameAsset(asset.getNames());
			assetDto.setBrand(asset.getBrandId().getNames());
			assetDto.setTypeAsset(asset.getAssetTypeId().getNames());
			assetDto.setStatusAsset(asset.getStatusAssetId().getNameStatusAsset());
			assetDto.setExpiredDate(asset.getExpiredDate());
			
			listResult.add(assetDto);
		}
		
		return listResult;
	}

	@Override
	public byte[] pdfAssetExpired() throws Exception {
		Users users = usersService.findById("1");
		Company company = users.getEmployeeId().getCompanyId();
		HashMap<String, Object> map = new HashMap<>();
		map.put("company", company.getNames());
		map.put("address", company.getAddress());
		map.put("website", company.getWebsite());
		map.put("telp", company.getPhoneNumber());
		map.put("fax", company.getFax());
		map.put("description", company.getDescription());
		map.put("logo", company.getId());
		map.put("title", "LICENSE ASSETS EXPIRED");
		
		byte[] data = JasperUtil.responseToByteArray(findExpiredAsset(), "asset-expired", map);
		
		return data;
	}

	@Override
	public ResDto sendEmailAssetExpiredReport() throws Exception {
		byte[] data = pdfAssetExpired();
		
		EmailHelper emailHelper = new EmailHelper();
		emailHelper.setAttach(data);
		emailHelper.setFileName("asset-expired.pdf");
		
		emailHandler.sendSimpleMessage("septianardi053@gmail.com", "License Asset Expired Report", "Expired Asset", emailHelper);
		
		ResDto resDto = new ResDto();
		resDto.setMsg("Send to Email");
		
		return resDto;
	}
	
	public void validationSave(Asset data) throws Exception {

		if (data.getAssetTypeId() == null || data.getBrandId() == null || data.getCompanyId() == null
				|| data.getInventoryId() == null || data.getInvoiceId() == null || data.getNames() == null
				|| data.getStatusAssetId() == null) {

			throw new ValidationGlexyException("Data not Complete");
		}

	}

	@Override
	public void validationUpdate(Asset data) throws Exception {
		if (data.getId() != null) {
			Asset asset = findById(data.getId());
			if (asset == null) {
				throw new ValidationGlexyException("Data not Found");
			}
		} else {
			throw new ValidationGlexyException("Data not Found");
		}

		if (data.getAssetTypeId() == null || data.getBrandId() == null || data.getCompanyId() == null
				|| data.getInventoryId() == null || data.getInvoiceId() == null || data.getNames() == null
				|| data.getStatusAssetId() == null) {

			throw new ValidationGlexyException("Data not Complete");
		}
	}
}
