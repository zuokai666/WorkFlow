package com.accounting.component.normalrepay;

import java.util.Map;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.accounting.exception.IllegalAccountException;
import com.accounting.model.Account;
import com.accounting.util.Constant;
import com.accounting.util.DB;

public class CouponAccountSetProcedure {
	
	private static final Logger log = LoggerFactory.getLogger(CouponAccountSetProcedure.class);
	
	public void run(Map<String, Object> map){
		int couponAccountId = (int) map.getOrDefault("couponAccountId", 1);
		Session session = DB.getSession();
		try {
			Account couponAccount = (Account) session.get(Account.class, couponAccountId);
			if(couponAccount.getType().equals(Constant.accounttype_company)){
				map.put("couponAccountId", couponAccountId);
				log.info("设置优惠券对公账户[{}]", couponAccountId);
			}else {
				throw new IllegalAccountException("账户["+couponAccountId+"]不是对公账户");
			}
		} finally {
			if(session != null){
				session.close();
			}
		}
	}
}