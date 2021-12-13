package glexy.dao;

import java.util.List;

import glexy.model.Company;

public interface CompanyDao {
	
	void insert(Company data) throws Exception;

	void update(Company data) throws Exception;

	Company findById(String id) throws Exception;
	
	List<Company> findAll() throws Exception;
	

}
