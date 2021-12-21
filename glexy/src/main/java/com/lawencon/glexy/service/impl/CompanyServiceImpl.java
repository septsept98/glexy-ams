package com.lawencon.glexy.service.impl;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.glexy.dao.CompanyDao;
import com.lawencon.glexy.dao.EmployeeDao;
import com.lawencon.glexy.dao.UsersDao;
import com.lawencon.glexy.exception.ValidationGlexyException;
import com.lawencon.glexy.model.Company;
import com.lawencon.glexy.model.Employee;
import com.lawencon.glexy.model.File;
import com.lawencon.glexy.model.Transactions;
import com.lawencon.glexy.model.Users;
import com.lawencon.glexy.service.CompanyService;
import com.lawencon.glexy.service.FileService;

@Service
public class CompanyServiceImpl extends BaseServiceImpl implements CompanyService {

	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private EmployeeDao employeeDao;

	@Override
	public Company save(Company data, MultipartFile files) throws Exception {
		try {

			Company company = data;
			company.setCreatedBy("3");
			company.setVersion(data.getVersion());
			
			File file = new File();
			file.setFiles(files.getBytes());
			String ext = files.getOriginalFilename();
			ext = ext.substring(ext.lastIndexOf(".")+1, ext.length());
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
		Company company = findById(data.getId());
		company.setNames(data.getNames());
		company.setUpdatedBy("1");
		
		begin();
		company = companyDao.saveOrUpdate(company);
		commit();
		return company;
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
	public Company findByCode(String Code) throws Exception {
		return companyDao.findByCode(Code);
	}

	@Override
	public void validationFk(String id) throws Exception {
		
		List<Employee> dataEmployee = employeeDao.findByCompanyId(id);
		if (dataEmployee != null) {

			throw new ValidationGlexyException("Company in Use");
		}
		
	}
	
	

}
