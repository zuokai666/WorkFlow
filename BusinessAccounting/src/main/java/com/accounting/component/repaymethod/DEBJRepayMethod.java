package com.accounting.component.repaymethod;

import java.math.BigDecimal;
import java.util.List;

import com.accounting.util.TM;

public class DEBJRepayMethod implements RepayMethod {
	
	@Override
	public void fire(List<RepaySchedule> repaySchedules, String bizDate, BigDecimal dayInterestRate, int loanTerm, BigDecimal loanAmount) {
		//按照360天年基准天数计算月利率
		double monthInterestRate = dayInterestRate.doubleValue() / 100 * 30;
		double repayMonthPrincipal = loanAmount.doubleValue() / loanTerm;
		BigDecimal repayMonthPrincipalBD = BigDecimal.valueOf(repayMonthPrincipal);
		double interestPrincipal = loanAmount.doubleValue();
		String tempDate = bizDate;
		for(int i=1;i<=loanTerm;i++){
			RepaySchedule plan = repaySchedules.get(i - 1);
			plan.setCurrentTerm(i);
			plan.setStartDate(tempDate);
			tempDate = TM.addMonth(tempDate, 1);
			plan.setEndDate(tempDate);
			plan.setPayDate(tempDate);
			plan.setInterestPrincipal(new BigDecimal(interestPrincipal));//计息本金,每次减去本金
			plan.setRepayPrincipal(repayMonthPrincipalBD);
			BigDecimal repayInterestBD = BigDecimal.valueOf(interestPrincipal * monthInterestRate);
			plan.setRepayInterest(repayInterestBD);//计息本金*月利率
			plan.setRepayAmount(repayMonthPrincipalBD.add(repayInterestBD));
			interestPrincipal = interestPrincipal - repayMonthPrincipal;
		}
	}
}