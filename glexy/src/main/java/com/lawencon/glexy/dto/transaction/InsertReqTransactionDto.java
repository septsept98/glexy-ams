package com.lawencon.glexy.dto.transaction;

import java.util.List;

import com.lawencon.glexy.model.TransactionDetail;
import com.lawencon.glexy.model.Transactions;

public class InsertReqTransactionDto {

	private Transactions dataTransaction;
	
	private List<TransactionDetail> dataDetailTransaction;
	
	private List<InsertReqDataAssetTransactionDto> dataAssetTransaction ;

	public Transactions getDataTransaction() {
		return dataTransaction;
	}

	public void setDataTransaction(Transactions dataTransaction) {
		this.dataTransaction = dataTransaction;
	}

	public List<TransactionDetail> getDataDetailTransaction() {
		return dataDetailTransaction;
	}

	public void setDataDetailTransaction(List<TransactionDetail> dataDetailTransaction) {
		this.dataDetailTransaction = dataDetailTransaction;
	}

	public List<InsertReqDataAssetTransactionDto> getDataAssetTransaction() {
		return dataAssetTransaction;
	}

	public void setDataAssetTransaction(List<InsertReqDataAssetTransactionDto> dataAssetTransaction) {
		this.dataAssetTransaction = dataAssetTransaction;
	}

}
