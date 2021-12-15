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
import com.lawencon.glexy.model.TransactionDetail;
import com.lawencon.glexy.service.TransactionDetailService;

@RestController
@RequestMapping("transaction-details")
public class TransactionDetailController {

	@Autowired
	private TransactionDetailService transactionDetailService;
	
	@GetMapping()
	public ResponseEntity<?> getAll() throws Exception {
		List<TransactionDetail> result = transactionDetailService.findAll();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("{id}")
	public ResponseEntity<?> getById(@PathVariable("id") String id) throws Exception {
		TransactionDetail result = transactionDetailService.findById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/details/{id}")
	public ResponseEntity<?> getByTr(@PathVariable("id") String id) throws Exception {
		List<TransactionDetail> result = transactionDetailService.findByTr(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<?> saveOrUpdate(@RequestBody TransactionDetail data) throws Exception {
		data = transactionDetailService.saveOrUpdate(data);
		
		InsertResDataDto id = new InsertResDataDto();
		id.setId(data.getId());
		
		InsertResDto result = new InsertResDto();
		result.setData(id);
		result.setMsg(MessageEnum.CREATED.getMsg());
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

}
