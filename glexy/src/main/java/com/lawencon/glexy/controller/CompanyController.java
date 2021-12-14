package com.lawencon.glexy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.glexy.model.Company;
import com.lawencon.glexy.service.CompanyService;

@RestController
@RequestMapping("companies")
public class CompanyController {
	
	@Autowired
	private CompanyService companyService;
	
	@GetMapping
	public ResponseEntity<?> getAll() throws Exception {
		List<Company> result = companyService.findAll();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<?> getById(@PathVariable("id") String id) throws Exception {
		Company result = companyService.findById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);

	}
	
	@PostMapping
	public ResponseEntity<?> insert(@RequestBody Company data) throws Exception {
		companyService.saveOrUpdate(data);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<?> update(@RequestBody Company data) throws Exception {
		companyService.saveOrUpdate(data);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> delete(@PathVariable("id") String id) throws Exception {
		boolean result = companyService.removeById(id);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	

}
