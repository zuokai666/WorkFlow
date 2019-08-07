package com.accounting.component.loan;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.accounting.component.repaymethod.RepayMethod;
import com.accounting.component.repaymethod.RepayMethodFactory;
import com.accounting.component.repaymethod.RepaySchedule;
import com.accounting.exception.UnSupportRepayMethodException;
import com.accounting.model.Coupon;
import com.accounting.model.Loan;
import com.accounting.model.RepayPlan;

/**
 * 
 * @author King
 * 
 */
public class RepayPlanInitProcedure {
	
	private static final Logger log = LoggerFactory.getLogger(RepayPlanInitProcedure.class);
	
	public void run(Session session,Map<String, Object> map){
		BigDecimal dayInterestRate = (BigDecimal) map.get("dayInterestRate");
		Integer loanTerm = (Integer) map.get("loanTerm");
		BigDecimal loanAmount = (BigDecimal) map.get("loanAmount");
		String repaymethodName = (String) map.get("repaymethod");
		Loan initLoan = (Loan) map.get("initLoan");
		String bizDate = (String) map.get("businessDate");
		
		List<RepaySchedule> repaySchedules = new ArrayList<>();
		for(int i=0;i<loanTerm;i++){
			RepayPlan plan = new RepayPlan();
			plan.setLoanId(initLoan.getId());
			plan.setAccrueInterest(new BigDecimal(0));
			repaySchedules.add(plan);
		}
		RepayMethod repayMethod = RepayMethodFactory.getInstance().getRepayMethod(repaymethodName);
		if(repayMethod == null){
			throw new UnSupportRepayMethodException("不支持的还款方式:" + repaymethodName);
		}
		repayMethod.fire(repaySchedules, bizDate, dayInterestRate, loanTerm, loanAmount);
		Coupon coupon = (Coupon) map.get("coupon");
		int waiveTerm = coupon==null ? 0 : coupon.getN();
		for(int i=0;i<repaySchedules.size();i++){
			RepayPlan plan = (RepayPlan) repaySchedules.get(i);
			plan.setRepayInterestPenalty(new BigDecimal(0));
			plan.setRepayPrincipalPenalty(new BigDecimal(0));
			if(waiveTerm == 0){
				plan.setWaiveInterest(new BigDecimal(0));
			}else {
				plan.setWaiveInterest(plan.getRepayInterest());
				waiveTerm--;
			}
			plan.setWaiveInterestPenalty(new BigDecimal(0));
			plan.setWaivePrincipal(new BigDecimal(0));
			plan.setWaivePrincipalPenalty(new BigDecimal(0));
			session.persist(plan);
		}
		log.info("还款计划表数据插入成功");
	}
}