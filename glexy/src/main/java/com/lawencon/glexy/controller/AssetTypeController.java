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

import com.lawencon.glexy.model.AssetType;
import com.lawencon.glexy.service.AssetTypeService;

@RestController
@RequestMapping("asset-types")
public class AssetTypeController {
	
	@Autowired
	private AssetTypeService assetTypeService;
	
	@GetMapping
	public ResponseEntity<?> getAll() throws Exception {
		List<AssetType> result = assetTypeService.findAll();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<?> getById(@PathVariable("id") String id) throws Exception {
		AssetType result = assetTypeService.findById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);

	}
	
	@PostMapping
	public ResponseEntity<?> insert(@RequestBody AssetType data) throws Exception {
		assetTypeService.saveOrUpdate(data);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<?> update(@RequestBody AssetType data) throws Exception {
		assetTypeService.saveOrUpdate(data);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> delete(@PathVariable("id") String id) throws Exception {
		boolean result = assetTypeService.removeById(id);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	

}
