package com.lawencon.glexy.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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

			Inventory inventory = inventoryService.findByCode(inven.getCode()); // code
			if (inventory != null) {
				stock = inventory.getStock() + stockInven;
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

			Brand brand = brandService.findById(asset.getBrandId().getId());
			if (brand == null) {
				throw new ValidationGlexyException("Brand Not Found");
			}
			Company company = companyService.findById(asset.getCompanyId().getId());
			if (company == null) {
				throw new ValidationGlexyException("Company Not Found");
			}
			StatusAsset statusAsset = statusAssetService.findById(asset.getStatusAssetId().getId());
			if (statusAsset == null) {
				throw new ValidationGlexyException("Status Asset Not Found");
			}
			AssetType assetType = assetTypeService.findById(asset.getAssetTypeId().getId());

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
		String update = "";
		Asset asset = findById(data.getId());
		if (asset == null) {
			throw new ValidationGlexyException("Asset Not Found");
		}
		StatusAsset statusAsset = new StatusAsset();
		Brand brand = new Brand();
		Company company = new Company();
		AssetType assetType = new AssetType();

		statusAsset = statusAssetService.findById(data.getStatusAssetId().getId());
		if (statusAsset == null) {
			throw new ValidationGlexyException("Status Asset Not Found");
		}

		brand = brandService.findById(data.getBrandId().getId());
		if (brand == null) {
			throw new ValidationGlexyException("Brand Not Found");
		}

		company = companyService.findById(data.getCompanyId().getId());
		if (company == null) {
			throw new ValidationGlexyException("Company Not Found");
		}

		assetType = assetTypeService.findById(data.getAssetTypeId().getId());
		if (assetType == null) {
			throw new ValidationGlexyException("Asset Type Not Found");
		}

		if (!asset.getAssetTypeId().getId().equals(data.getAssetTypeId().getId())
				|| !asset.getBrandId().getId().equals(data.getBrandId().getId())
				|| !asset.getCompanyId().getId().equals(data.getCompanyId().getId())
				|| asset.getExpiredDate() != data.getExpiredDate()) {
			update = "Update Asset";
		} else if (!asset.getStatusAssetId().getId().equals(data.getStatusAssetId().getId())) {
			update = "Update Status Asset";
		}

		asset.setStatusAssetId(statusAsset);
		asset.setBrandId(brand);
		asset.setCompanyId(company);
		asset.setAssetTypeId(assetType);
		asset.setExpiredDate(data.getExpiredDate());
		asset.setUpdatedBy(getIdAuth());

		begin();
		asset = assetDao.saveOrUpdate(asset);

		TrackAsset trackAsset = new TrackAsset();
		trackAsset.setCodeAsset(asset.getCode());
		trackAsset.setNameActivity(update);
		trackAsset.setDateActivity(LocalDate.now());
		trackAsset.setUserId(getIdAuth());
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
	public List<Asset> findByInvoice(String idInvoice) throws Exception {
		return assetDao.findByInvoiceId(idInvoice);
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
		ExcelUtil excelUtil = new ExcelUtil();
		try {
			excelUtil.initRead("Assets", file.getInputStream());
			begin();
			for (int i = 1; i < excelUtil.getRowCountInSheet(); i++) {
				Integer stock = 0;
				int index = 0;
				Inventory inven = new Inventory();
				Integer stockInven = null;

				if (excelUtil.getCellData(i, 0) == null) {
					break;
				}
				stockInven = Double.valueOf(excelUtil.getCellData(i, 1)).intValue();
				String codeInsert = excelUtil.getCellData(i, 2);
				Inventory inventory = inventoryService.findByCode(codeInsert); // bycode
				if (inventory == null) {
					inven.setStock(stockInven);
					inven.setNameAsset(excelUtil.getCellData(i, 0));
					inven.setLatestStock(stockInven);
					inven.setCode(codeInsert);
					stock = stockInven;
				} else {
					index = inventory.getStock();
					stock = inventory.getStock() + stockInven;
					int latest = inventory.getLatestStock() + stockInven;
					inven.setId(inventory.getId());
					inven.setNameAsset(inventory.getNameAsset());
					inven.setCode(inventory.getCode());
					inven.setStock(stock);
					inven.setLatestStock(latest);
					inven.setCreatedBy(inventory.getCreatedBy());
					inven.setCreatedAt(inventory.getCreatedAt());
					inven.setIsActive(inventory.getIsActive());
					inven.setVersion(inventory.getVersion());

				}

				inven = inventoryService.saveOrUpdate(inven);

				Invoice invoice = new Invoice();
				invoice.setCode(excelUtil.getCellData(i, 3));
				invoice.setTotalPrice(excelUtil.getCellData(i, 4));
				invoice.setPurchaseDate(LocalDate.now());

				invoiceService.save(invoice);
				Brand brand = brandService.findByCode(excelUtil.getCellData(i, 5));
				if (brand == null) {
					throw new ValidationGlexyException("Brand Not Found");
				}

				AssetType assetType = assetTypeService.findByCode(excelUtil.getCellData(i, 6));
				if (assetType == null) {
					throw new ValidationGlexyException("Asset Type Not Found");
				}

				StatusAsset statusAsset = statusAssetService.findByCode(excelUtil.getCellData(i, 8));
				if (statusAsset == null) {
					throw new ValidationGlexyException("Status Asset Not Found");
				}

				Company company = companyService.findByCode(excelUtil.getCellData(i, 9));
				if (company == null) {
					throw new ValidationGlexyException("Company Not Found");
				}

				for (int j = index; j < stock; j++) {
					Asset assetInsert = new Asset();
					String codeAsset = generateCode(inven.getCode(), company.getCode(), stock, j);
					assetInsert.setAssetTypeId(assetType);

					assetInsert.setBrandId(brand);
					assetInsert.setCompanyId(company);

					if (excelUtil.getCellData(i, 7) != null) {
//						DateTimeFormatter patern = DateTimeFormatter.ofPattern("yyyy-MM-dd");

//						LocalDate date = LocalDate.parse(excelUtil.getCellData(i, 7), patern);
						Date date = excelUtil.getCellData(i, 7);
						LocalDate localDate = date.toInstant()
							      .atZone(ZoneId.systemDefault())
							      .toLocalDate();
						assetInsert.setExpiredDate(localDate);

					}

					assetInsert.setStatusAssetId(statusAsset);
					assetInsert.setNames(inven.getNameAsset());
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
					trackAsset.setCreatedBy(getIdAuth());
					trackAsset.setIsActive(true);

					trackAssetService.saveOrUpdate(trackAsset);
				}

			}
			commit();

		} catch (Exception e) {
			rollback();
			e.printStackTrace();
			throw new Exception("fail to store excel data: " + e.getMessage());
		}

	}

	@Override
	public Asset updateImage(Asset data, MultipartFile assetImg) throws Exception {

		try {

			File imgAsset = new File();
			Asset asset = assetDao.findById(data.getId());
			data.setNames(asset.getNames());
			data.setCode(asset.getCode());
			data.setExpiredDate(asset.getExpiredDate());
			data.setAssetTypeId(asset.getAssetTypeId());
			data.setStatusAssetId(asset.getStatusAssetId());
			data.setBrandId(asset.getBrandId());
			data.setCompanyId(asset.getCompanyId());
			data.setCreatedBy(asset.getCreatedBy());
			data.setCreatedAt(asset.getCreatedAt());
			data.setInventoryId(asset.getInventoryId());
			data.setInvoiceId(asset.getInvoiceId());
			data.setVersion(asset.getVersion());
			data.setIsActive(asset.getIsActive());

			imgAsset.setFiles(assetImg.getBytes());
			String ext = assetImg.getOriginalFilename();
			ext = ext.substring(ext.lastIndexOf(".") + 1, ext.length());
			imgAsset.setExtension(ext);

			File imgInsert = fileService.findByByte(imgAsset.getFile(), ext);
			if (imgInsert != null) {
				data.setAssetImg(imgInsert);
			} else {
				imgAsset = fileService.saveOrUpdate(imgAsset);
				data.setAssetImg(imgAsset);
			}
			data.setUpdatedBy(getIdAuth());

			begin();
			data = assetDao.saveOrUpdate(data);
			commit();

		} catch (Exception e) {
			rollback();
			e.printStackTrace();
			throw new Exception("fail to store excel data: " + e.getMessage());
		}

		return data;
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

		for (int i = 0; i < listAsset.size(); i++) {
			ReportDataExpiredAsset assetDto = new ReportDataExpiredAsset();
			Asset asset = listAsset.get(i);
			assetDto.setImage(asset.getAssetImg().getFile());
			assetDto.setIdAsset(asset.getId());
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
		Users users = usersService.findByIdAuth();
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

		byte[] data = JasperUtil.responseToByteArray(findExpiredAsset(), "assets-expired", map);

		return data;
	}

	@Override
	public ResDto sendEmailAssetExpiredReport() throws Exception {
		byte[] data = pdfAssetExpired();

		EmailHelper emailHelper = new EmailHelper();
		emailHelper.setAttach(data);
		emailHelper.setFileName("asset-expired.pdf");

		Users users = usersService.findByIdAuth();

		emailHandler.sendReport(users.getEmail(), "License Asset Expired Report", "Expired Asset", emailHelper);

		ResDto resDto = new ResDto();
		resDto.setMsg("Send to Email");

		return resDto;
	}

	public void validationSave(Asset data) throws Exception {
		if (data != null) {
			if (data.getAssetTypeId() == null || data.getBrandId() == null || data.getCompanyId() == null
					|| data.getInventoryId() == null || data.getInvoiceId() == null
					|| data.getStatusAssetId() == null) {

				throw new ValidationGlexyException("Data not Complete");
			}
		} else {
			throw new ValidationGlexyException("Data Empty");
		}
	}

	@Override
	public void validationUpdate(Asset data) throws Exception {
		if (data != null) {
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
		} else {
			throw new ValidationGlexyException("Data Empty");
		}
	}

	@Override
	public List<Asset> searchAssetGeneral(String search) throws Exception {
		List<Asset> result = new ArrayList<>();
		if (search.isBlank()) {
			return result;
		} else {
			result = assetDao.searchAssetGeneral(search);
			return result;
		}
	}

	@Override
	public List<Asset> findAssetByInventBrand(String inventId, String brandId) throws Exception {
		List<Asset> result = new ArrayList<>();
		if (inventId.isBlank() && brandId.isBlank()) {
			return result;
		} else {
			result = assetDao.findAssetByInventBrand(inventId, brandId);
			return result;
		}
	}

	@Override
	public byte[] downloadTemplateExcel() throws Exception {
		ExcelUtil excelUtil = new ExcelUtil();
		List<Brand> brandList = brandService.findAll();
		List<Company> companyList = companyService.findAll();
		List<AssetType> assetTypeList = assetTypeService.findAll();
		List<StatusAsset> statusAssetList = statusAssetService.findAll();
		List<Inventory> inventoryList = inventoryService.findAll();

		excelUtil.initWrite("Assets", "Inventory Code", "Brand Code", "Asset Type Code", "Status Asset Code",
				"Company Code");

		List<String> header = new ArrayList<>();
		header.add("Code");
		header.add("Name");

		excelUtil.setCellValue("Brand Code", 0, header, true);
		List<String> brand = new ArrayList<>();
		for (int i = 0; i < brandList.size(); i++) {
			brand.add(brandList.get(i).getCode());
			brand.add(brandList.get(i).getNames());
			excelUtil.setCellValue("Brand Code", i + 1, brand, false);
			brand.removeAll(brand);
		}

		excelUtil.setCellValue("Company Code", 0, header, true);
		List<String> company = new ArrayList<>();
		for (int i = 0; i < companyList.size(); i++) {
			company.add(companyList.get(i).getCode());
			company.add(companyList.get(i).getNames());
			excelUtil.setCellValue("Company Code", i + 1, company, false);
			company.removeAll(company);
		}

		excelUtil.setCellValue("Asset Type Code", 0, header, true);
		List<String> assetType = new ArrayList<>();
		for (int i = 0; i < assetTypeList.size(); i++) {
			assetType.add(assetTypeList.get(i).getCode());
			assetType.add(assetTypeList.get(i).getNames());
			excelUtil.setCellValue("Asset Type Code", i + 1, assetType, false);
			assetType.removeAll(assetType);
		}

		excelUtil.setCellValue("Status Asset Code", 0, header, true);
		List<String> statusAsset = new ArrayList<>();
		for (int i = 0; i < statusAssetList.size(); i++) {
			statusAsset.add(statusAssetList.get(i).getCodeStatusAsset());
			statusAsset.add(statusAssetList.get(i).getNameStatusAsset());
			excelUtil.setCellValue("Status Asset Code", i + 1, statusAsset, false);
			statusAsset.removeAll(statusAsset);
		}

		excelUtil.setCellValue("Inventory Code", 0, header, true);
		List<String> inventory = new ArrayList<>();
		for (int i = 0; i < inventoryList.size(); i++) {
			inventory.add(inventoryList.get(i).getCode());
			inventory.add(inventoryList.get(i).getNameAsset());
			excelUtil.setCellValue("Inventory Code", i + 1, inventory, false);
			inventory.removeAll(inventory);
		}

		String[] headerAsset = { "Name Asset", "Quantity", "Code Inventory", "Invoice Code", "Total Price",
				"Brand Code", "Asset Type Code", "Expired Date", "Status Asset Code", "Company Code" };

		excelUtil.setCellValue("Assets", 0, Arrays.asList(headerAsset), true);

		byte[] template = excelUtil.getByteArrayFile();

		return template;
	}

	@Override
	public List<Asset> findAssetUndeployable() throws Exception {

		return assetDao.findAssetUndeployable();
	}

	@Override
	public List<Asset> findAssetPending() throws Exception {

		return assetDao.findAssetPending();
	}

	@Override
	public List<Asset> findAssetArchived() throws Exception {
		// TODO Auto-generated method stub
		return assetDao.findAssetArchived();
	}

}
