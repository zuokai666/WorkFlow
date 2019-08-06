package com.accounting;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.accounting.bean.BizResponse;
import com.accounting.service.AccountingServiceImpl;
import com.accounting.util.Constant;
import com.accounting.util.DB;

public class BatchBaApp {
	
	private static final Logger log = LoggerFactory.getLogger(BatchBaApp.class);
	
	public static void main(String[] args) {
		DB.printDate();
		int testConnection = 1000 * 10000;
		ThreadPoolExecutor executor=
					new ThreadPoolExecutor(30, 30, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
		for(int i=0;i<testConnection;i++){
			try {
				executor.execute(() -> loan());
			} catch (RejectedExecutionException e) {
				e.printStackTrace();
				break;
			}
		}
		executor.shutdown();
	}
	
	public static void loan() {
		AccountingServiceImpl accountingService = new AccountingServiceImpl();
		Map<String, Object> map = new HashMap<>();
		map.put("loanAmount", new BigDecimal(1_0000));
		map.put("loanTerm", 3);
		map.put("dayInterestRate", new BigDecimal(0.05));
//		map.put("accountId", 1);
		map.put("repaymethod", Constant.repaymethod_debx);
		BizResponse result = accountingService.loan(map);
		if(result.getResult().equals("1")){
			log.info(result.toString());
		}else {
			log.error(result.toString());
		}
	}
}