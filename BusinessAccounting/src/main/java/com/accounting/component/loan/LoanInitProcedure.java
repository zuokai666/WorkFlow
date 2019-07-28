package com.accounting.component.loan;

import java.math.BigDecimal;
import java.util.Map;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.accounting.model.Loan;
import com.accounting.util.Db;
import com.accounting.util.TM;

public class LoanInitProcedure {
	
	private static final Logger log = LoggerFactory.getLogger(LoanInitProcedure.class);
	
	/**
	 * 检查此账户是否有未结清的借款
	 * @param session
	 * @param map
	 */
	public void run(Session session,Map<String, Object> map){
		BigDecimal dayInterestRate = (BigDecimal) map.get("dayInterestRate");
		Integer loanTerm = (Integer) map.get("loanTerm");
		BigDecimal loanAmount = (BigDecimal) map.get("loanAmount");
		String bizDate = Db.getBusinessDate(session);
		Loan loan = new Loan();
		loan.setTerm(loanTerm);
		int accountId = (int) map.get("accountId");
		loan.setAccountId(accountId);
		loan.setLoanPrincipal(loanAmount);
		loan.setRepayMethod("DEBX");
		loan.setLoanDate(bizDate);
		loan.setStartDate(bizDate);
		loan.setEndDate(TM.addMonth(bizDate, loanTerm));
		loan.setPaidPrincipal(new BigDecimal(0));
		loan.setPaidInterest(new BigDecimal(0));
		loan.setPaidAmount(new BigDecimal(0));
		loan.setDayInterestRate(dayInterestRate);
		loan.setRepayPrincipal(new BigDecimal(0));
		loan.setRepayInterest(new BigDecimal(0));
		loan.setRepayAmount(new BigDecimal(0));
		session.persist(loan);
		map.put("initLoan", loan);
		log.info("借据表数据插入成功");
	}
}