package com.accounting.component.repaymethod;

import java.math.BigDecimal;
import java.util.List;

public interface RepayMethod {
	
	void fire(List<RepaySchedule> repaySchedules, String bizDate, BigDecimal dayInterestRate, int loanTerm, BigDecimal loanAmount);
}