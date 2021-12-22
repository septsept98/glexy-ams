package com.lawencon.glexy.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.lawencon.base.ConnHandler;
import com.lawencon.glexy.model.Asset;
import com.lawencon.glexy.model.AssetType;
import com.lawencon.glexy.model.Brand;
import com.lawencon.glexy.model.Company;
import com.lawencon.glexy.model.Employee;
import com.lawencon.glexy.model.Inventory;
import com.lawencon.glexy.model.Invoice;
import com.lawencon.glexy.model.Location;
import com.lawencon.glexy.model.Roles;
import com.lawencon.glexy.model.StatusAsset;
import com.lawencon.glexy.model.StatusTransaction;
import com.lawencon.glexy.model.TransactionDetail;
import com.lawencon.glexy.model.Transactions;
import com.lawencon.glexy.model.Users;

@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
@DataJpaTest 
class TransactionDetailDaoTest {
	
	@Autowired
	private TransactionDetailDao transactionDetailDao;
	
	@Autowired
	private TransactionDao transactionDao;
	
	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private LocationDao locationDao;
	
	@Autowired
	private UsersDao usersDao;
	
	@Autowired
	private AssetDao assetDao;

	@Autowired
	private CompanyDao companyDao;

	@Autowired
	private InvoiceDao invoiceDao;

	@Autowired
	private InventoryDao inventoryDao;

	@Autowired
	private AssetTypeDao assetTypeDao;

	@Autowired
	private StatusAssetDao statusAssetDao;

	@Autowired
	private BrandDao brandDao;

	@Autowired
	private RolesDao rolesDao;
	
	@Autowired
	private StatusTransactionDao statusTransactionDao;
	
	private String idInserted = "";
	
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
		
		Roles roles = new Roles();
		roles.setNameRole("HRD");
		roles.setCode("HR");
		roles.setCreatedBy("1");
		roles.setCreatedAt(LocalDateTime.now());
		roles.setIsActive(true);
		
		rolesDao.saveOrUpdate(roles);
		
		Employee employee = new Employee();
		employee.setNameEmployee("Bhondan");
		employee.setNip("3171767886674");
		employee.setPhoneNumber("086545664554");
		employee.setGender("Laki-Laki");
		employee.setCreatedBy("1");
		employee.setCreatedAt(LocalDateTime.now());
		employee.setIsActive(true);
		
		employeeDao.saveOrUpdate(employee);
		
		Users users = new Users();
		users.setEmail("bhondan@gmail.com");
		users.setPass("bondanlalayeye");
		users.setRolesId(roles);
		users.setEmployeeId(employee);
		users.setCreatedBy("1");
		users.setCreatedAt(LocalDateTime.now());
		users.setIsActive(true);
		
		usersDao.saveOrUpdate(users);
		
		Location location = new Location();
		location.setNamePlace("Ruang Bootcamp");
		location.setCode("BTP");
		location.setCompanyId(company);
		location.setCreatedBy("2");
		location.setCreatedAt(LocalDateTime.now());
		location.setIsActive(true);
		
		locationDao.saveOrUpdate(location);
		
		Transactions transactions = new Transactions();
		
		transactions.setCheckOutDate(LocalDate.parse("2025-03-03",dateTimeFormatter));
		transactions.setCodeTransaction("HKIY22345");
		transactions.setDescription("Untuk Bootcamp");
		transactions.setQuantity(1);
		transactions.setAssetGeneralId(asset);
		transactions.setEmployeeId(employee);
		transactions.setLocationId(location);
		transactions.setUserId(users);
		transactions.setCreatedBy("2");
		transactions.setCreatedAt(LocalDateTime.now());
		transactions.setIsActive(true);
		
		transactionDao.saveOrUpdate(transactions);
		
		StatusTransaction statusTransaction = new StatusTransaction();
		statusTransaction.setCodeStatusTr("RTD");
		statusTransaction.setNameStatusTr("Ready to Deploy");
		statusTransaction.setStatusAssetId(statusAsset);
		statusTransaction.setCreatedBy("2");
		statusTransaction.setCreatedAt(LocalDateTime.now());
		statusTransaction.setIsActive(true);
		
		statusTransactionDao.saveOrUpdate(statusTransaction);
		
		TransactionDetail transactionDetail = new TransactionDetail();
		transactionDetail.setTransactionId(transactions);
		transactionDetail.setDateCheckin(LocalDate.now());
		transactionDetail.setAssetId(asset);
		transactionDetail.setDurationDate(LocalDate.parse("2025-05-05",dateTimeFormatter));
		transactionDetail.setStatusAssetCheckoutId(statusAsset);
		transactionDetail.setStatusEmail(false);
		transactionDetail.setCreatedBy("2");
		transactionDetail.setCreatedAt(LocalDateTime.now());
		transactionDetail.setIsActive(true);
		
		transactionDetailDao.saveOrUpdate(transactionDetail);
		
		ConnHandler.commit();
		
		idInserted = transactionDetail.getId();

		assertNotNull(transactionDetail.getId());
	}
	
	@Test
	@Order(2)
	public void checkAll() throws Exception{
		List<TransactionDetail> transDetList = transactionDetailDao.findAll();
		assertNotNull(transDetList);
	}
	
	@Test
	@Order(3)
	public void checkById() throws Exception {
		TransactionDetail transactionDetail = transactionDetailDao.findById(idInserted);
		assertNotNull(transactionDetail);
	}


}
