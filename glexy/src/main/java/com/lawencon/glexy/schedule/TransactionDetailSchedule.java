package com.lawencon.glexy.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.lawencon.glexy.service.TransactionDetailService;

@Component
public class TransactionDetailSchedule {
	
	@Autowired
	private TransactionDetailService transactionDetailService;

	@Scheduled(fixedDelay = 60000)
	public void expiredAssetReminder() throws Exception {
		
	transactionDetailService.expDurationAssign();	
		
	}
	
}
