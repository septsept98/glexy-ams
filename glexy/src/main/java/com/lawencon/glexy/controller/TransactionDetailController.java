package com.lawencon.glexy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.glexy.constant.MessageEnum;
import com.lawencon.glexy.dto.InsertResDataDto;
import com.lawencon.glexy.dto.InsertResDto;
import com.lawencon.glexy.dto.ResDto;
import com.lawencon.glexy.dto.UpdateResDataDto;
import com.lawencon.glexy.dto.UpdateResDto;
import com.lawencon.glexy.helper.ReportDataTransactionOutDate;
import com.lawencon.glexy.model.TransactionDetail;
import com.lawencon.glexy.service.TransactionDetailService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping("transaction-details")
public class TransactionDetailController {

	@Autowired
	private TransactionDetailService transactionDetailService;
	
	@GetMapping()
	@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = TransactionDetail.class)))
	public ResponseEntity<?> getAll() throws Exception {
		List<TransactionDetail> result = transactionDetailService.findAll();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("all-check-in")
	@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = TransactionDetail.class)))
	public ResponseEntity<?> getAllCheckIn() throws Exception {
		List<TransactionDetail> result = transactionDetailService.findAllCheckIn();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("all-not-check-in")
	@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = TransactionDetail.class)))
	public ResponseEntity<?> getAllNotCheckIn() throws Exception {
		List<TransactionDetail> result = transactionDetailService.findAllNotCheckIn();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/exp-duration")
	@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = TransactionDetail.class)))
	public ResponseEntity<?> getAllExpDurationAssign() throws Exception {
		List<TransactionDetail> result = transactionDetailService.expDurationAssign();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("{id}")
	@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = TransactionDetail.class)))
	public ResponseEntity<?> getById(@PathVariable("id") String id) throws Exception {
		TransactionDetail result = transactionDetailService.findById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/details/{id}")
	@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = TransactionDetail.class)))
	public ResponseEntity<?> getByTr(@PathVariable("id") String id) throws Exception {
		List<TransactionDetail> result = transactionDetailService.findByTr(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/details-not-checkin/{id}")
	@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = TransactionDetail.class)))
	public ResponseEntity<?> getNotCheckIn(@PathVariable("id") String id) throws Exception {
		List<TransactionDetail> result = transactionDetailService.findByTrNotCheckIn(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/details-checkin/{id}")
	@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = TransactionDetail.class)))
	public ResponseEntity<?> getCheckIn(@PathVariable("id") String id) throws Exception {
		List<TransactionDetail> result = transactionDetailService.findByTrCheckIn(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping
	@ApiResponse(responseCode = "201", description = "successful operation", content = @Content(schema = @Schema(implementation = InsertResDataDto.class)))
	public ResponseEntity<?> insert(@RequestBody TransactionDetail data) throws Exception {
		data = transactionDetailService.saveOrUpdate(data);
		
		InsertResDataDto id = new InsertResDataDto();
		id.setId(data.getId());
		
		InsertResDto result = new InsertResDto();
		result.setData(id);
		result.setMsg(MessageEnum.CREATED.getMsg());
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PutMapping
	@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = UpdateResDataDto.class)))
	public ResponseEntity<?> update(@RequestBody TransactionDetail data) throws Exception {
		data = transactionDetailService.saveOrUpdate(data);
		
		UpdateResDataDto ver = new UpdateResDataDto();
		ver.setVersion(data.getVersion());
		
		UpdateResDto result = new UpdateResDto();
		result.setData(ver);
		result.setMsg(MessageEnum.UPDATED.getMsg());
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/pdf")
	public byte[] generatePdf() throws Exception, JRException {
		
		byte[] data = transactionDetailService.pdfTransactionOutDate();
		
		return data;
	}
	
	@GetMapping("/out-date")
	public ResponseEntity<?> getData() throws Exception {
		List<ReportDataTransactionOutDate> result = transactionDetailService.reportAllDataOutDate();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/send-email")
	public ResponseEntity<?> sendEmailTrackAsset() throws Exception, JRException {
		
		ResDto resDto = transactionDetailService.sendEmailTrxExpiredReport();
		
		return new ResponseEntity<>(resDto, HttpStatus.OK);
	}
	
}
