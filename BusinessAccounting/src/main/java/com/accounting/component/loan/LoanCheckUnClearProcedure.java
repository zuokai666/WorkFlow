package com.accounting.component.loan;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.accounting.exception.UnClearedLoanException;
import com.accounting.model.Loan;

public class LoanCheckUnClearProcedure {
	
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(LoanCheckUnClearProcedure.class);
	
	/**
	 * 检查此账户是否有未结清的借款
	 * @param session
	 * @param map
	 */
	public void run(Session session,Map<String, Object> map){
		int accountId = (int) map.get("accountId");
		@SuppressWarnings("unchecked")
		List<Loan> loans = session
				.createQuery("from Loan where accountId = :accountId and finishDate is null")
				.setParameter("accountId", accountId)
				.list();
		if(loans.size() > 0){
			throw new UnClearedLoanException("借据号["+loans.get(0).getId()+"]未结清");
		}
	}
}