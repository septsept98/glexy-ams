package com.lawencon.glexy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
}
