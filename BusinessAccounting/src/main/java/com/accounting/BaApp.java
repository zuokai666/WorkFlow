package com.accounting;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.accounting.service.AccountingServiceImpl;
import com.accounting.service.ScheduleServiceImpl;
import com.accounting.util.DB;

public class BaApp {
	
	private static final Logger log = LoggerFactory.getLogger(BaApp.class);
	
	public static void main(String[] args) {
		DB.printDate();
		loan();
//		while(true){
//			ScheduleServiceImpl scheduleService = new ScheduleServiceImpl();
//			scheduleService.dayCut();
//			
//			AccountingServiceImpl accountingService = new AccountingServiceImpl();
//			accountingService.batchCharge();
//			try {
//				Thread.sleep(1 * 1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
		
		
		
		ScheduleServiceImpl scheduleService = new ScheduleServiceImpl();
		scheduleService.dayCut();
//		
//		AccountingServiceImpl accountingService = new AccountingServiceImpl();
//		accountingService.batchCharge();
	}
	
	public static void loan() {
		AccountingServiceImpl accountingService = new AccountingServiceImpl();
		Map<String, Object> map = new HashMap<>();
		map.put("loanAmount", new BigDecimal(1_0000));
		map.put("loanTerm", 3);
		map.put("dayInterestRate", new BigDecimal(0.05));
		map.put("accountId", 1);
		boolean result = accountingService.loan(map);
		if(result){
			log.info("借款成功");
		}else {
			log.info("借款失败");
		}
	}
}