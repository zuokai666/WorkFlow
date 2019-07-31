package com.accounting.component.repaymethod;

import java.math.BigDecimal;
import java.util.List;

import com.accounting.util.TM;

public class XXHBRepayMethod implements RepayMethod {
	
	@Override
	public void fire(List<RepaySchedule> repaySchedules, String bizDate, BigDecimal dayInterestRate, int loanTerm, BigDecimal loanAmount) {
		//按照360天年基准天数计算月利率
		double monthInterestRate = dayInterestRate.doubleValue() / 100 * 30;
		double interestPrincipal = loanAmount.doubleValue();
		String tempDate = bizDate;
		BigDecimal repayInterestBD = BigDecimal.valueOf(interestPrincipal * monthInterestRate);//计息本金*月利率
		BigDecimal interestPrincipalBD = loanAmount;//计息本金一直不变
		BigDecimal repayMonthPrincipalBD = new BigDecimal(0);
		for(int i=1;i<=loanTerm;i++){
			if(i == loanTerm){//最后一期
				repayMonthPrincipalBD = loanAmount;
			}
			RepaySchedule plan = repaySchedules.get(i - 1);
			plan.setCurrentTerm(i);
			plan.setStartDate(tempDate);
			tempDate = TM.addMonth(tempDate, 1);
			plan.setEndDate(tempDate);
			plan.setPayDate(tempDate);
			plan.setInterestPrincipal(interestPrincipalBD);//计息本金
			plan.setRepayPrincipal(repayMonthPrincipalBD);
			plan.setRepayInterest(repayInterestBD);
			plan.setRepayAmount(repayMonthPrincipalBD.add(repayInterestBD));
		}
	}
}