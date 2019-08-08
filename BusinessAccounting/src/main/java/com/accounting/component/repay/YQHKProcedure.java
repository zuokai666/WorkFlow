package com.accounting.component.repay;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.accounting.component.batchcharge.OverdueRepayProcedure;
import com.accounting.exception.MistakeReapyModeException;
import com.accounting.model.RepayPlan;

//逾期还款
public class YQHKProcedure {
	
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(YQHKProcedure.class);
	
	/**
	 * 
	 * @param session
	 * @param map loanId businessDate
	 */
	public void run(Session session,Map<String, Object> map){
		int loanId = (int) map.get("loanId");
		String businessDate = (String) map.get("businessDate");
		@SuppressWarnings("unchecked")
		List<RepayPlan> repayPlans = session
				.createQuery("from RepayPlan r where r.loanId = :loanId and r.endDate < :ddate and r.finishDate is null")
				.setParameter("loanId", loanId)
				.setParameter("ddate", businessDate)
				.list();
		if(repayPlans.size() !=1){
			throw new MistakeReapyModeException("不符合逾期还款");
		}else {
			OverdueRepayProcedure overdueRepayProcedure = new OverdueRepayProcedure();
			overdueRepayProcedure.run(map, session, loanId);
		}
	}
}