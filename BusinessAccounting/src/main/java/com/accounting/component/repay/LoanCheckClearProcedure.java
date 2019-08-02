package com.accounting.component.repay;

import java.util.Map;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.accounting.exception.LoanClearException;
import com.accounting.model.Loan;

public class LoanCheckClearProcedure {
	
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(LoanCheckClearProcedure.class);
	
	/**
	 * 检查借据是否已经结清
	 * @param session
	 * @param map
	 */
	public void run(Session session,Map<String, Object> map){
		int loanId = (int) map.get("loanId");
		Loan loan = (Loan) session
				.createQuery("from Loan where loanId = :loanId")
				.setParameter("loanId", loanId)
				.list()
				.get(0);
		if(loan.getFinishDate() != null){
			throw new LoanClearException("结清的借据:"+loan.getId());
		}
	}
}