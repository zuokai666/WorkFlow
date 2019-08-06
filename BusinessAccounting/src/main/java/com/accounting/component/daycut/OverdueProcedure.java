package com.accounting.component.daycut;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.accounting.model.Loan;
import com.accounting.model.RepayPlan;
import com.accounting.util.Constant;

/**
 * 逾期计算
 */
public class OverdueProcedure {
	
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(OverdueProcedure.class);
	
	public void run(Session session, Loan loan, Map<String, Object> map){
		String handleDate = loan.getHandleDate();
		@SuppressWarnings("unchecked")
		List<RepayPlan> repayPlans = session
		.createQuery("from RepayPlan where loanId = :loanId and endDate < :ddate and finishDate is null")
		.setParameter("loanId", loan.getId())
		.setParameter("ddate", handleDate)
		.list();
		if(repayPlans.size() > 0){
			loan.setLoanStatus(Constant.loanstatus_yq);
			loan.setOverdueDays(loan.getOverdueDays() + 1);
			if(loan.getMaxOverdueDays() < loan.getOverdueDays()){
				loan.setMaxOverdueDays(loan.getOverdueDays());//重置最大逾期天数
			}
		}
		for(int i=0;i<repayPlans.size();i++){
			RepayPlan repayPlan = repayPlans.get(i);
			repayPlan.setRemark("逾期计罚息");
			repayPlan.setRepayInterestPenalty(computeRepayInterestPenalty(loan, repayPlan));
			repayPlan.setRepayPrincipalPenalty(computeRepayPrincipalPenalty(loan, repayPlan));
			session.persist(repayPlan);
		}
	}
	
	//计算利息罚息=逾期利息*日利率*1.5*逾期天数
	private BigDecimal computeRepayInterestPenalty(Loan loan, RepayPlan repayPlan){
		return repayPlan.getRepayInterest()
				.multiply(loan.getDayInterestRate().divide(new BigDecimal("100.00")))
				.multiply(BigDecimal.valueOf(1.5))
				.multiply(new BigDecimal(loan.getOverdueDays()));
	}
	
	//计算本金罚息=逾期本金*日利率*1.5*逾期天数
	private BigDecimal computeRepayPrincipalPenalty(Loan loan, RepayPlan repayPlan){
		return repayPlan.getRepayPrincipal()
				.multiply(loan.getDayInterestRate().divide(new BigDecimal("100.00")))
				.multiply(BigDecimal.valueOf(1.5))
				.multiply(new BigDecimal(loan.getOverdueDays()));
	}
}