package com.lawencon.glexy.service;

import java.util.List;

import com.lawencon.glexy.dto.ResDto;
import com.lawencon.glexy.helper.ReportDataTransactionOutDate;
import com.lawencon.glexy.model.TransactionDetail;

public interface TransactionDetailService {

	List<TransactionDetail> findAll() throws Exception;

	TransactionDetail findById(String id) throws Exception;

	List<TransactionDetail> findByTr(String id) throws Exception;

	TransactionDetail saveOrUpdate(TransactionDetail data) throws Exception;
	
	List<TransactionDetail> expDurationAssign() throws Exception;
	
	List<TransactionDetail> findAllOutDate() throws Exception;

	List<ReportDataTransactionOutDate> reportAllDataOutDate() throws Exception;
	
	byte[] pdfTransactionOutDate() throws Exception;
	
	ResDto sendEmailTrxExpiredReport() throws Exception;
	
	void validationSave(TransactionDetail data) throws Exception;
	
	List<TransactionDetail> findByTrNotCheckIn(String id) throws Exception;

}
