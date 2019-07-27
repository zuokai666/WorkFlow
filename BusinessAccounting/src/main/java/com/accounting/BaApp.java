package com.accounting;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.accounting.service.AccountingServiceImpl;
import com.accounting.util.Db;

public class BaApp {
	
	private static final Logger log = LoggerFactory.getLogger(BaApp.class);
	
	public static void main(String[] args) {
		Db.printDate();
		loan();
//		dayCut();
	}
	
	public static void dayCut(){
		AccountingServiceImpl accountingService = new AccountingServiceImpl();
		accountingService.dayCut();
	}
	
	public static void loan() {
		AccountingServiceImpl accountingService = new AccountingServiceImpl();
		Map<String, Object> map = new HashMap<>();
		map.put("loanAmount", new BigDecimal(1_0000_0000));
		map.put("loanTerm", 12);
		map.put("dayInterestRate", new BigDecimal(0.05));
		boolean result = accountingService.loan(map);
		if(result){
			log.info("借款成功");
		}else {
			log.info("借款失败");
		}
	}
}