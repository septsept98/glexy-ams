package com.lawencon.glexy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.glexy.constant.MessageEnum;
import com.lawencon.glexy.dto.InsertResDataDto;
import com.lawencon.glexy.dto.InsertResDto;
import com.lawencon.glexy.dto.transaction.InsertReqDataAssetTransactionDto;
import com.lawencon.glexy.dto.transaction.InsertReqTransactionDto;
import com.lawencon.glexy.model.Asset;
import com.lawencon.glexy.model.Transactions;
import com.lawencon.glexy.service.TransactionService;

@RestController
@RequestMapping("transactions")
public class TransactionsController {
	
	@Autowired
	private TransactionService transactionService;
	
	@GetMapping()
	public ResponseEntity<?> getAll() throws Exception {
		List<Transactions> result = transactionService.findAll();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("{id}")
	public ResponseEntity<?> getById(@PathVariable("id") String id) throws Exception {
		Transactions result = transactionService.findById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/asset-details")
	public ResponseEntity<?> getAsset(@RequestBody InsertReqDataAssetTransactionDto data) throws Exception {
		List<Asset> result = transactionService.findAssetDetail(data);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<?> insert(@RequestBody InsertReqTransactionDto data) throws Exception {
		transactionService.saveOrUpdate(data);
		
		InsertResDataDto id = new InsertResDataDto();
		id.setId(data.getDataTransaction().getId());
		
		InsertResDto result = new InsertResDto();
		result.setData(id);
		result.setMsg(MessageEnum.CREATED.getMsg());
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
