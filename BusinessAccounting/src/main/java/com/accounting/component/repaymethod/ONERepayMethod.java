package com.accounting.component.repaymethod;

import java.math.BigDecimal;
import java.util.List;

import com.accounting.util.TM;

public class ONERepayMethod implements RepayMethod {
	
	@Override
	public void fire(List<RepaySchedule> repaySchedules, String bizDate, BigDecimal dayInterestRate, int loanTerm, BigDecimal loanAmount) {
		//按照360天年基准天数计算月利率
		double monthInterestRate = dayInterestRate.doubleValue() / 100 * 30;
		double interestPrincipal = loanAmount.doubleValue();
		BigDecimal repayInterestBD = BigDecimal.valueOf(interestPrincipal * monthInterestRate * loanTerm);
		String tempDate = bizDate;
		RepaySchedule plan = repaySchedules.get(0);
		repaySchedules.clear();
		repaySchedules.add(plan);
		plan.setCurrentTerm(1);
		plan.setStartDate(tempDate);
		tempDate = TM.addMonth(tempDate, loanTerm);
		plan.setEndDate(tempDate);
		plan.setInterestPrincipal(loanAmount);//计息本金=贷款本金
		plan.setRepayPrincipal(loanAmount);//贷款本金
		plan.setRepayInterest(repayInterestBD);
		plan.setRepayAmount(loanAmount.add(repayInterestBD));
	}
}