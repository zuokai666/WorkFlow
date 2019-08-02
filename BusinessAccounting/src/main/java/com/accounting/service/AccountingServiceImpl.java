package com.accounting.service;

import java.util.Map;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.accounting.component.loan.LoanCheckUnClearProcedure;
import com.accounting.component.loan.LoanInitProcedure;
import com.accounting.component.loan.LoanInterceptProcedure;
import com.accounting.component.loan.RepayPlanInitProcedure;
import com.accounting.component.normalrepay.DayEndWaitProcedure;
import com.accounting.component.repay.AdaptiveRepayProcedure;
import com.accounting.component.repay.LoanCheckClearProcedure;
import com.accounting.util.DB;

public class AccountingServiceImpl {
	
	private static final Logger log = LoggerFactory.getLogger(AccountingServiceImpl.class);
	
	public boolean loan(Map<String, Object> map){
		Session session = null;
		try {
			session = DB.getSession();
			session.getTransaction().begin();
//			TableClearProcedure tableClearProcedure = new TableClearProcedure();
//			tableClearProcedure.run(session, map);
			LoanCheckUnClearProcedure loanCheckProcedure = new LoanCheckUnClearProcedure();
			loanCheckProcedure.run(session, map);
			LoanInitProcedure loanInitProcedure = new LoanInitProcedure();
			loanInitProcedure.run(session, map);
			LoanInterceptProcedure loanInterceptProcedure = new LoanInterceptProcedure();
			loanInterceptProcedure.run(session, map);
			RepayPlanInitProcedure repayPlanInitProcedure = new RepayPlanInitProcedure();
			repayPlanInitProcedure.run(session, map);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			log.error("", e);
			session.getTransaction().rollback();
		} finally {
			if(session != null){
				session.close();
			}
		}
		return false;
	}
	
	public boolean repay(Map<String, Object> map){
		Session session = null;
		try {
			session = DB.getSession();
			session.getTransaction().begin();
			DayEndWaitProcedure dayEndWaitProcedure = new DayEndWaitProcedure();
			dayEndWaitProcedure.run(map);
			LoanCheckClearProcedure loanCheckClearProcedure = new LoanCheckClearProcedure();
			loanCheckClearProcedure.run(session, map);
			AdaptiveRepayProcedure adaptiveRepayProcedure = new AdaptiveRepayProcedure();
			adaptiveRepayProcedure.run(session, map);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			log.error("", e);
			session.getTransaction().rollback();
		} finally {
			if(session != null){
				session.close();
			}
		}
		return false;
	}
}