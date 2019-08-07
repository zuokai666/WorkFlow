package com.accounting.component.normalrepay;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CouponAccountSetProcedure {
	
	private static final Logger log = LoggerFactory.getLogger(CouponAccountSetProcedure.class);
	
	public void run(Map<String, Object> map){
		map.put("couponAccountId", 1);
		log.info("设置优惠券对公账户[{}]", 1);
	}
}