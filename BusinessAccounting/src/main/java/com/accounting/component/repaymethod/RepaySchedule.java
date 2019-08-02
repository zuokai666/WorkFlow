package com.accounting.component.repaymethod;

import java.math.BigDecimal;

public interface RepaySchedule {
	
	void setCurrentTerm(int currentTerm);
	void setStartDate(String startDate);
	void setEndDate(String endDate);
	void setInterestPrincipal(BigDecimal interestPrincipal);
	void setRepayPrincipal(BigDecimal repayPrincipal);
	void setRepayInterest(BigDecimal repayInterest);
	void setRepayAmount(BigDecimal repayAmount);
}