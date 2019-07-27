package com.accounting.component.loan;

import java.math.BigDecimal;
import java.util.Map;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.accounting.model.Loan;
import com.accounting.model.RepayPlan;
import com.accounting.util.Db;
import com.accounting.util.TM;

/**
 * 当前严重依赖于等额本息算法，不具有扩展性
 * 
 * @author King
 * 
 */
public class RepayPlanInitProcedure {
	
	private static final Logger log = LoggerFactory.getLogger(RepayPlanInitProcedure.class);
	
	private double getMonthPay(BigDecimal dayInterestRate,Integer loanTerm,BigDecimal loanAmount){
		double p = loanAmount.doubleValue();//贷款本金
		double r = dayInterestRate.doubleValue() / 100 * 30;//月利率
		double temp = 1.0;
		for(int j=0;j<loanTerm;j++){
			temp = temp * (1 + r);
		}
		double monthPay = (p * r * temp) / (temp - 1);//月供
		return monthPay;
	}
	
	public void run(Session session,Map<String, Object> map){
		BigDecimal dayInterestRate = (BigDecimal) map.get("dayInterestRate");
		Integer loanTerm = (Integer) map.get("loanTerm");
		BigDecimal loanAmount = (BigDecimal) map.get("loanAmount");
		Loan initLoan = (Loan) map.get("initLoan");
		String bizDate = Db.getBusinessDate(session);
		
		//计算月供
		double monthPay = getMonthPay(dayInterestRate, loanTerm, loanAmount);
		double monthInterestRate = dayInterestRate.doubleValue() / 100 * 30;
		
		double interestPrincipal = loanAmount.doubleValue();
		String tempDate = bizDate;
		double totalRepayPrincipal = 0.0;
		for(int i=1;i<=loanTerm;i++){
			RepayPlan plan = new RepayPlan();
			plan.setCurrentTerm(i);
			plan.setLoanId(initLoan.getId());
			plan.setStartDate(tempDate);
			tempDate = TM.addMonth(tempDate, 1);
			plan.setEndDate(tempDate);
			plan.setPayDate(tempDate);
			plan.setInterestPrincipal(new BigDecimal(interestPrincipal));//计息本金,每次减去本金
			
			double repayInterest = interestPrincipal * monthInterestRate;
			totalRepayPrincipal = totalRepayPrincipal + repayInterest;
			double repayPrincipal = monthPay - repayInterest;
			
			plan.setRepayPrincipal(new BigDecimal(repayPrincipal));
			plan.setRepayInterest(new BigDecimal(repayInterest));
			plan.setRepayAmount(new BigDecimal(monthPay));
			plan.setAccrueInterest(new BigDecimal(0));
			plan.setDayInterestRate(dayInterestRate);
			session.persist(plan);
			interestPrincipal = interestPrincipal - repayPrincipal;
		}
		log.info("还款计划表数据插入成功");
		initLoan.setRepayPrincipal(loanAmount);
		initLoan.setRepayInterest(new BigDecimal(totalRepayPrincipal));
		initLoan.setRepayAmount(new BigDecimal(totalRepayPrincipal + loanAmount.doubleValue()));
		session.persist(initLoan);
		log.info("更新借据表应还金额信息");
	}
}