package com.lawencon.glexy.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.lawencon.base.ConnHandler;
import com.lawencon.glexy.model.Asset;
import com.lawencon.glexy.model.AssetType;
import com.lawencon.glexy.model.Brand;
import com.lawencon.glexy.model.Company;
import com.lawencon.glexy.model.Inventory;
import com.lawencon.glexy.model.Invoice;
import com.lawencon.glexy.model.StatusAsset;
import com.lawencon.glexy.model.TrackAsset;

import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;

@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
@DataJpaTest 
class AssetDaoTest {

	@Autowired
	private AssetDao assetDao;
	@Autowired
	private BrandDao brandDao;
	@Autowired
	private CompanyDao companyDao;
	@Autowired
	private StatusAssetDao statusAssetDao;
	@Autowired
	private AssetTypeDao assetTypeDao;
	@Autowired
	private InvoiceDao invoiceDao;
	@Autowired
	private InventoryDao inventoryDao;
	@Autowired
	private TrackAssetDao trackAssetDao;
	
	private String idInserted = "";
	private String idInvent = "";
	private String idBrand = "";
	private String idCompany = "";
	
	@Test
	@Order(1)
	public void insert() throws Exception {
		ConnHandler.begin();
		
		Brand brand = new Brand();
		brand.setNames("Acer");
		brand.setCode("ACR");
		brand.setCreatedBy("2");
		brand.setCreatedAt(LocalDateTime.now());
		brand.setIsActive(true);
		
		brandDao.saveOrUpdate(brand);
		
		Company company = new Company();
		company.setNames("Lawencon");
		company.setCode("LWN");
		company.setDescription("Perusahaan A");
		company.setAddress("Jl.Tebet Raya");
		company.setEmail("lawencon@gmail.com");
		company.setWebsite("www.lawencon.com");
		company.setPhoneNumber("085432345667");
		company.setFax("02198765");
		company.setCreatedBy("2");
		company.setCreatedAt(LocalDateTime.now());
		company.setIsActive(true);
		
		companyDao.saveOrUpdate(company);
		
		StatusAsset statusAsset = new StatusAsset();
		statusAsset.setCodeStatusAsset("SA1");
		statusAsset.setNameStatusAsset("Deployable");
		statusAsset.setCreatedBy("2");
		statusAsset.setCreatedAt(LocalDateTime.now());
		statusAsset.setIsActive(true);
		
		statusAssetDao.saveOrUpdate(statusAsset);
		
		AssetType assetType = new AssetType();
		assetType.setNames("General");
		assetType.setCode("GNR");
		assetType.setCreatedBy("2");
		assetType.setCreatedAt(LocalDateTime.now());
		assetType.setIsActive(true);
		
		assetTypeDao.saveOrUpdate(assetType);
		
		Invoice invoice = new Invoice();
		invoice.setCode("BHIY355433");
		invoice.setPurchaseDate(LocalDate.now());
		BigDecimal bigDecimal = new BigDecimal(2500000);
		invoice.setTotalPrice(bigDecimal);
		invoice.setCreatedBy("2");
		invoice.setCreatedAt(LocalDateTime.now());
		invoice.setIsActive(true);
		
		invoiceDao.saveOrUpdate(invoice);
		
		Inventory inventory = new Inventory();
		inventory.setNameAsset("Laptop");
		inventory.setCode("LTP");
		inventory.setStock(1);
		inventory.setLatestStock(1);
		inventory.setCreatedBy("2");
		inventory.setCreatedAt(LocalDateTime.now());
		inventory.setIsActive(true);
		
		inventoryDao.saveOrUpdate(inventory);
		
		Asset asset = new Asset();
		asset.setNames("Laptop");
		asset.setCode("LTPLWN1");
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		asset.setExpiredDate(LocalDate.parse("2023-03-03",dateTimeFormatter));
		asset.setInvoiceId(invoice);
		asset.setCompanyId(company);
		asset.setAssetTypeId(assetType);
		asset.setInventoryId(inventory);
		asset.setStatusAssetId(statusAsset);
		asset.setCreatedBy("2");
		asset.setCreatedAt(LocalDateTime.now());
		asset.setIsActive(true);
		
		assetDao.saveOrUpdate(asset);
		
		TrackAsset trackAsset = new TrackAsset();
		trackAsset.setCodeAsset(asset.getCode());
		trackAsset.setNameActivity("New");
		trackAsset.setDateActivity(LocalDate.now());
		trackAsset.setUserId("2");
		trackAsset.setTransactionCode("CCC");
		trackAsset.setCreatedBy("2");
		trackAsset.setCreatedAt(LocalDateTime.now());
		trackAsset.setIsActive(true);
		
		trackAssetDao.saveOrUpdate(trackAsset);
		
		ConnHandler.commit();
		
		idInserted = asset.getId();
		idBrand = brand.getId();
		idCompany = company.getId();
		idInvent = inventory.getId();
		
		assertNotNull(asset.getId());
	}
	
	@Test
	@Order(2)
	public void update() throws Exception {
		Asset asset = assetDao.findById(idInserted);

		ConnHandler.begin();
		
		asset.setNames("Komputer");
		asset.setUpdatedBy("3");
		asset.setUpdatedAt(LocalDateTime.now());
		asset.setIsActive(true);
		asset.setVersion(0L);
		
		Asset assetCheck = assetDao.saveOrUpdate(asset);
		ConnHandler.commit();

		assertEquals(1, assetCheck.getVersion());
		assertEquals("Komputer", assetCheck.getNames());
	}
	
	@Test
	@Order(3)
	public void checkAll() throws Exception{
		List<Asset> assetList = assetDao.findAll();
		assertNotNull(assetList);
	}
	
	@Test
	@Order(4)
	public void checkById() throws Exception {
		Asset asset = assetDao.findById(idInserted);
		assertNotNull(asset);
	}
	
	@Test
	@Order(5)
	public void checkByBrandId() throws Exception {
		List<Asset> asset = assetDao.findByBrandId(idBrand);
		assertNotNull(asset);
	}
	
	@Test
	@Order(6)
	public void checkByCompanyId() throws Exception {
		List<Asset> asset = assetDao.findByCompanyId(idCompany);
		assertNotNull(asset);
	}
	
	@Test
	@Order(7)
	public void checkByInventId() throws Exception {
		List<Asset> asset = assetDao.findByInvent(idInvent);
		assertNotNull(asset);
	}
	
	@Test
	@Order(8)
	public void checkAllDeployAsset() throws Exception {
		List<Asset> asset = assetDao.findAllDeployAsset();
		assertNotNull(asset);
	}
	
	@Test
	@Order(9)
	public void checkAllGeneralAsset() throws Exception {
		List<Asset> asset = assetDao.findAllGeneralAsset();
		assertNotNull(asset);
	}
	
	@Test
	@Order(10)
	public void delete() throws Exception {
		ConnHandler.begin();
		boolean isDeleted = assetDao.removeById(idInserted);
		ConnHandler.commit();

		assertTrue(isDeleted);
	}
	
	

}
