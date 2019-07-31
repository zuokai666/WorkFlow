package com.accounting.service;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.accounting.component.loan.LoanCheckProcedure;
import com.accounting.component.loan.LoanInitProcedure;
import com.accounting.component.loan.LoanInterceptProcedure;
import com.accounting.component.loan.RepayPlanInitProcedure;
import com.accounting.component.normalrepay.BatchChargeProcedure;
import com.accounting.component.normalrepay.DayEndWaitProcedure;
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
			LoanCheckProcedure loanCheckProcedure = new LoanCheckProcedure();
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
	
	public void batchCharge(){
		try {
			Map<String, Object> map = new HashMap<>();
			DayEndWaitProcedure dayEndWaitProcedure = new DayEndWaitProcedure();
			dayEndWaitProcedure.run(map);
			BatchChargeProcedure batchChargeProcedure = new BatchChargeProcedure();
			batchChargeProcedure.run(map);
		} catch (Exception e) {
			log.error("", e);
		}
	}
}