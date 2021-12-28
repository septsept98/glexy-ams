package com.lawencon.glexy.service.impl;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lawencon.glexy.dao.AssetDao;
import com.lawencon.glexy.dao.CompanyDao;
import com.lawencon.glexy.dao.EmployeeDao;
import com.lawencon.glexy.dao.LocationDao;
import com.lawencon.glexy.exception.ValidationGlexyException;
import com.lawencon.glexy.model.Asset;
import com.lawencon.glexy.model.Company;
import com.lawencon.glexy.model.Employee;
import com.lawencon.glexy.model.File;
import com.lawencon.glexy.model.Location;
import com.lawencon.glexy.service.CompanyService;
import com.lawencon.glexy.service.FileService;

@Service
public class CompanyServiceImpl extends BaseGlexyServiceImpl implements CompanyService {

	@Autowired
	private CompanyDao companyDao;

	@Autowired
	private FileService fileService;

	@Autowired
	private EmployeeDao employeeDao;

	@Autowired
	private AssetDao assetDao;

	@Autowired
	private LocationDao locationDao;

	@Override
	public Company save(Company data, MultipartFile files) throws Exception {
		try {

			validationSave(data);
			Company company = data;
			company.setCreatedBy("3");
			company.setVersion(data.getVersion());

			data.setCreatedBy(getIdAuth());

			File file = new File();
			file.setFiles(files.getBytes());
			String ext = files.getOriginalFilename();
			ext = ext.substring(ext.lastIndexOf(".") + 1, ext.length());
			file.setExtension(ext);
			begin();

			file = fileService.saveOrUpdate(file);

			data.setCompanyImg(file);

			data = companyDao.saveOrUpdate(data);

			commit();

		} catch (Exception e) {
			e.printStackTrace();
			rollback();
		}
		return data;
	}

	@Override
	public Company update(Company data) throws Exception {
		validationUpdate(data);
		Company company = findById(data.getId());
		data.setNames(company.getNames());
		data.setCreatedBy(company.getCreatedBy());
		data.setCreatedAt(company.getCreatedAt());
		data.setUpdatedBy(getIdAuth());
		data.setVersion(data.getVersion());

		begin();
		data = companyDao.saveOrUpdate(data);
		commit();
		return data;
	}

	@Override
	public Company findById(String id) throws Exception {
		Company result = new Company();
		try {
			result = companyDao.findById(id);
		} catch (NoResultException e) {
			e.printStackTrace();
			throw new NoResultException("Company not found");
		}
		return result;
	}

	@Override
	public List<Company> findAll() throws Exception {
		return companyDao.findAll();
	}

	@Override
	public boolean removeById(String id) throws Exception {
		boolean result = false;
		try {
			validationFk(id);
			begin();
			result = companyDao.removeById(id);
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
		}
		return result;
	}

	@Override
	public List<Company> searchByNameCode(String search) throws Exception {
		return companyDao.searchByNameCode(search);
	}

	@Override
	public Company findByCode(String code) throws Exception {
		return companyDao.findByCode(code);
	}

	@Override
	public void validationFk(String id) throws Exception {

		List<Employee> dataEmployee = employeeDao.findByCompanyId(id);
		List<Asset> dataAsset = assetDao.findByCompanyId(id);
		List<Location> dataLocation = locationDao.findByCompanyId(id);
		if (dataEmployee != null || dataAsset != null || dataLocation != null) {

			throw new ValidationGlexyException("Company in Use");
		}

	}

	@Override
	public void validationSave(Company data) throws Exception {
		if (data != null) {
			if (data.getAddress() == null || data.getCode() == null || data.getDescription() == null
					|| data.getEmail() == null || data.getFax() == null || data.getNames() == null
					|| data.getPhoneNumber() == null || data.getWebsite() == null) {

				throw new ValidationGlexyException("Data not Complete");

			}
		} else {
			throw new ValidationGlexyException("Data Empty");
		}
	}

	@Override
	public void validationUpdate(Company data) throws Exception {
		if (data != null) {
			if (data.getId() != null) {
				Company company = findById(data.getId());
				if (company == null) {
					throw new ValidationGlexyException("Data not Found");
				}
			} else {
				throw new ValidationGlexyException("Data not Found");
			}
			if (data.getAddress() == null || data.getCode() == null || data.getDescription() == null
					|| data.getEmail() == null || data.getFax() == null || data.getNames() == null
					|| data.getPhoneNumber() == null || data.getWebsite() == null) {

				throw new ValidationGlexyException("Data not Complete");

			}
		} else {
			throw new ValidationGlexyException("Data Empty");
		}
	}

}
