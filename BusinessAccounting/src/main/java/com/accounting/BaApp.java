package com.accounting;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.accounting.bean.BizResponse;
import com.accounting.service.AccountingServiceImpl;
import com.accounting.service.ScheduleServiceImpl;
import com.accounting.util.Constant;
import com.accounting.util.DB;

public class BaApp {
	
	private static final Logger log = LoggerFactory.getLogger(BaApp.class);
	
	public static void main(String[] args) {
		DB.printDate();
//		loan();
		
//		while(true){
//			ScheduleServiceImpl scheduleService = new ScheduleServiceImpl();
//			scheduleService.dayCut();
////			scheduleService.batchCharge();
//			repay();
//			try {
//				Thread.sleep(1 * 1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
		
		
		repay();
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
		map.put("repaymethod", Constant.repaymethod_debx);
//		map.put("repaymethod", Constant.repaymethod_debj);
//		map.put("repaymethod", Constant.repaymethod_xxhb);
//		map.put("repaymethod", Constant.repaymethod_one);
		BizResponse result = accountingService.loan(map);
		log.info(result.toString());
	}
	
	public static void repay() {
		AccountingServiceImpl accountingService = new AccountingServiceImpl();
		Map<String, Object> map = new HashMap<>();
		map.put("loanId", 14);
//		map.put("repaymode", Constant.repaymode_dqhk);
//		map.put("repaymode", Constant.repaymode_tqjqCur);
		map.put("repaymode", Constant.repaymode_tqjqAll);
		BizResponse result = accountingService.repay(map);
		log.info(result.toString());
	}
}