package com.accounting.component.loan;

import java.util.Map;

import org.hibernate.LockMode;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.accounting.exception.IllegalCouponException;
import com.accounting.model.Coupon;
import com.accounting.util.Constant;

public class CouponCheckProcedure {
	
	private static final Logger log = LoggerFactory.getLogger(CouponCheckProcedure.class);
	
	public void run(Session session,Map<String, Object> map){
		int accountId = (int) map.get("accountId");
		String bizDate = (String) map.get("businessDate");
		if(map.containsKey("couponId")){
			int couponId = (int) map.remove("couponId");//防止后面以此判断有优惠券
			Coupon coupon = session.get(Coupon.class, couponId, LockMode.PESSIMISTIC_WRITE);
			if(coupon != null && coupon.getAccountId() == accountId){
				if(!coupon.getUseStatus().equals(Constant.couponuse_unuse)){
					log.info("优惠券[{}]已使用,不可再次使用", couponId);
				}else if(coupon.getEndDate().compareTo(bizDate) < 0){
					log.info("优惠券[{}]已失效,不可再次使用", couponId);
				}else {
					coupon.setUseStatus(Constant.couponuse_used);
					coupon.setUseDate(bizDate);
					log.info("账户[{}]可以使用优惠券[{}]", accountId, couponId);
					map.put("coupon", coupon);
				}
			}else {
				throw new IllegalCouponException("优惠券["+couponId+"]不合法");
			}
		}
	}
}