package com.accounting.component.daycut;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.accounting.model.RepayPlan;
import com.accounting.util.Db;
import com.accounting.util.TM;

/**
 * 计提利息计算
 * 先不考虑数据量大的情况
 * 
 */
public class AccrueInterestProcedure {
	
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(AccrueInterestProcedure.class);
	
	public void run(Map<String, Object> map){
		Session session = Db.getSession();
		@SuppressWarnings("unchecked")
		List<RepayPlan> repayPlans = session
		.createQuery("from RepayPlan where startDate < :ddate and endDate >= :ddate")
		.setParameter("ddate", map.get("businessDate"))
		.list();
		for(int i=0;i<repayPlans.size();i++){
			RepayPlan repayPlan = repayPlans.get(i);
			if(repayPlan.getEndDate().equals(map.get("businessDate"))){//还款日，修正计提利息
				repayPlan.setAccrueInterest(repayPlan.getRepayInterest());
			}else {
				double lastAccrueInterest = repayPlan.getAccrueInterest().doubleValue();
				double dayReapyInterest = repayPlan.getRepayInterest().doubleValue() / TM.intervalDate(repayPlan.getStartDate(), repayPlan.getEndDate());
				repayPlan.setAccrueInterest(new BigDecimal(lastAccrueInterest + dayReapyInterest));
			}
		}
	}
}