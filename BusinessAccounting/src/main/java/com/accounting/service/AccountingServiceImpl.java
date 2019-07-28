package com.accounting.service;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.accounting.component.daycut.AccrueInterestProcedure;
import com.accounting.component.daycut.DayCutProcedure;
import com.accounting.component.loan.LoanInitProcedure;
import com.accounting.component.loan.RepayPlanInitProcedure;
import com.accounting.component.loan.TableClearProcedure;
import com.accounting.util.Db;

public class AccountingServiceImpl {
	
	private static final Logger log = LoggerFactory.getLogger(AccountingServiceImpl.class);
	
	public boolean loan(Map<String, Object> map){
		Session session = null;
		try {
			session = Db.getSession();
			session.getTransaction().begin();
			TableClearProcedure tableClearProcedure = new TableClearProcedure();
			tableClearProcedure.run(session, map);
			LoanInitProcedure loanInitProcedure = new LoanInitProcedure();
			loanInitProcedure.run(session, map);
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
	
	public void dayCut(){
		Session session = null;
		try {
			session = Db.getSession();
			session.getTransaction().begin();
			Map<String, Object> map = new HashMap<>();
			DayCutProcedure dayCutProcedure = new DayCutProcedure();
			dayCutProcedure.run(session, map);
			AccrueInterestProcedure accrueInterestProcedure = new AccrueInterestProcedure();
			accrueInterestProcedure.run(session, map);
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("", e);
			session.getTransaction().rollback();
		} finally {
			if(session != null){
				session.close();
			}
		}
	}
}