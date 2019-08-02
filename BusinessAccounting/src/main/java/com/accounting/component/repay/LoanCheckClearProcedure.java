package com.accounting.component.repay;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.accounting.exception.LoanClearException;
import com.accounting.exception.UnFindLoanException;
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
		@SuppressWarnings("unchecked")
		List<Loan> loans = session
				.createQuery("from Loan where id = :loanId")
				.setParameter("loanId", loanId)
				.list();
		if(loans.size() != 1){
			throw new UnFindLoanException("借据["+loanId+"]未找到");
		}
		if(loans.get(0).getFinishDate() != null){
			throw new LoanClearException("借据["+loanId+"]已结清");
		}
	}
}