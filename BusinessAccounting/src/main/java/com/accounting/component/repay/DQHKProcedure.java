package com.accounting.component.repay;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.accounting.component.normalrepay.DueRepayProcedure;
import com.accounting.exception.MistakeReapyModeException;
import com.accounting.model.RepayPlan;

public class DQHKProcedure {
	
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(DQHKProcedure.class);
	
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
				.createQuery("from RepayPlan r where r.loanId = :loanId and r.endDate = :ddate and r.finishDate is null")
				.setParameter("loanId", loanId)
				.setParameter("ddate", businessDate)
				.list();
		if(repayPlans.size() !=1){
			throw new MistakeReapyModeException("没有找到符合到期还款的条件:size=" + repayPlans.size());
		}else {
			DueRepayProcedure dueRepayProcedure = new DueRepayProcedure();
			dueRepayProcedure.run(map, session, loanId);
		}
	}
}