package com.accounting.component.repaymethod;

import java.math.BigDecimal;
import java.util.List;

import com.accounting.util.TM;

public class DEBXRepayMethod implements RepayMethod {
	
	@Override
	public void fire(List<RepaySchedule> repaySchedules, String bizDate, BigDecimal dayInterestRate, int loanTerm, BigDecimal loanAmount) {
		//计算月供
		double monthPay = getMonthPay(dayInterestRate, loanTerm, loanAmount);
		//按照360天年基准天数计算月利率
		double monthInterestRate = dayInterestRate.doubleValue() / 100 * 30;
		
		double interestPrincipal = loanAmount.doubleValue();
		String tempDate = bizDate;
		for(int i=1;i<=loanTerm;i++){
			RepaySchedule plan = repaySchedules.get(i - 1);
			plan.setCurrentTerm(i);
			plan.setStartDate(tempDate);
			tempDate = TM.addMonth(tempDate, 1);
			plan.setEndDate(tempDate);
			plan.setInterestPrincipal(new BigDecimal(interestPrincipal));//计息本金,每次减去本金
			
			double repayInterest = interestPrincipal * monthInterestRate;
			double repayPrincipal = monthPay - repayInterest;
			
			plan.setRepayPrincipal(new BigDecimal(repayPrincipal));
			plan.setRepayInterest(new BigDecimal(repayInterest));
			plan.setRepayAmount(new BigDecimal(monthPay));
			interestPrincipal = interestPrincipal - repayPrincipal;
		}
	}
	
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
}