package com.accounting.component.daycut;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.accounting.model.Loan;
import com.accounting.model.RepayPlan;
import com.accounting.util.TM;

/**
 * 计提利息计算
 * 本金分为，结转本金，未结转本金
 * 结转本金包括已还和逾期
 */
public class AccrueInterestProcedure {
	
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(AccrueInterestProcedure.class);
	
	public void run(Session session, Loan loan, Map<String, Object> map){
		String handleDate = loan.getHandleDate();
		@SuppressWarnings("unchecked")
		List<RepayPlan> repayPlans = session
		.createQuery("from RepayPlan where loanId = :loanId and startDate < :ddate and endDate >= :ddate and finishDate is null")
		.setParameter("loanId", loan.getId())
		.setParameter("ddate", handleDate)
		.list();
		for(int i=0;i<repayPlans.size();i++){
			RepayPlan repayPlan = repayPlans.get(i);
			if(repayPlan.getEndDate().equals(handleDate)){//还款日，修正计提利息
				repayPlan.setAccrueInterest(repayPlan.getRepayInterest());
			}else {
				int intervalDays = TM.intervalDate(repayPlan.getStartDate(), repayPlan.getEndDate());
				BigDecimal intervalDaysBD = new BigDecimal(intervalDays);
				repayPlan.setAccrueInterest(repayPlan.getAccrueInterest().add(repayPlan.getRepayInterest().divide(intervalDaysBD, 2, BigDecimal.ROUND_HALF_DOWN)));
			}
			session.persist(repayPlan);
		}
	}
}