package com.lawencon.glexy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.glexy.model.StatusTransaction;
import com.lawencon.glexy.service.StatusTransactionService;


@RestController
@RequestMapping("status-transactions")
public class StatusTransactionController {

	@Autowired
	private StatusTransactionService statusTransactionService;
	
	@GetMapping()
	public ResponseEntity<?> getAll() throws Exception {
		List<StatusTransaction> result = statusTransactionService.findAll();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("{id}")
	public ResponseEntity<?> getById(@PathVariable("id") String id) throws Exception {
		StatusTransaction result = statusTransactionService.findById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<?> saveOrUpdate(@RequestBody StatusTransaction data) throws Exception {
		data = statusTransactionService.saveOrUpdate(data);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id") String id) throws Exception {
		boolean data = statusTransactionService.removeById(id);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
}
