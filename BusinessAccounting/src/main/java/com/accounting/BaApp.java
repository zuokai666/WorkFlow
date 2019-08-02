package com.accounting;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.accounting.service.AccountingServiceImpl;
import com.accounting.service.ScheduleServiceImpl;
import com.accounting.util.Constant;
import com.accounting.util.DB;

public class BaApp {
	
	private static final Logger log = LoggerFactory.getLogger(BaApp.class);
	
	public static void main(String[] args) {
		DB.printDate();
//		loan();
		while(true){
			ScheduleServiceImpl scheduleService = new ScheduleServiceImpl();
			scheduleService.dayCut();
			scheduleService.batchCharge();
			try {
				Thread.sleep(1 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
		
//		ScheduleServiceImpl scheduleService = new ScheduleServiceImpl();
//		scheduleService.dayCut();
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
//		map.put("repaymethod", Constant.repaymethod_debx);
//		map.put("repaymethod", Constant.repaymethod_debj);
//		map.put("repaymethod", Constant.repaymethod_xxhb);
		map.put("repaymethod", Constant.repaymethod_one);
		boolean result = accountingService.loan(map);
		if(result){
			log.info("借款成功");
		}else {
			log.info("借款失败");
		}
	}
	
	public static void repay() {
		AccountingServiceImpl accountingService = new AccountingServiceImpl();
		Map<String, Object> map = new HashMap<>();
		map.put("loanId", 3);
		map.put("repaymode", Constant.repaymode_dqhk);
		boolean result = accountingService.repay(map);
		if(result){
			log.info("还款成功");
		}else {
			log.info("还款失败");
		}
	}
}